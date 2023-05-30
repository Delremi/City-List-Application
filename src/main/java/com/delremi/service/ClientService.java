package com.delremi.service;

import com.delremi.dto.ClientCreationDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.Client;
import com.delremi.model.User;
import com.delremi.repository.ClientRepository;
import com.delremi.security.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    public void saveClient(ClientCreationDto clientCreationDto) throws EntityNotFoundException {
        Client client = new Client();
        client.setFirstName(clientCreationDto.getFirstName());
        client.setLastName(clientCreationDto.getLastName());
        client.setUsername(clientCreationDto.getUsername());
        client.setEmail(clientCreationDto.getEmail());
        client.setAddress(clientCreationDto.getAddress());
        client.setCountry(clientCreationDto.getCountry());
        int userId = AuthUtils.getAuthenticatedUserId();
        User user = userService.getUser(userId);
        client.setCreatedBy(user);
        Client result = clientRepository.save(client);
        log.info("Saved Client with ID " + result.getId());
    }

    public List<Client> getClients() throws EntityNotFoundException {
        User user = userService.getUser(AuthUtils.getAuthenticatedUserId());
        return clientRepository.findAllByCreatedBy(user);
    }

    public Client getClient(int id) throws EntityNotFoundException {
        User user = userService.getUser(AuthUtils.getAuthenticatedUserId());
        Client client = clientRepository.findByIdAndCreatedBy(id, user);
        if (client == null) {
            throw new EntityNotFoundException(Client.class, id);
        }
        return client;
    }

    public void updateClient(int id, ClientCreationDto clientCreationDto) throws EntityNotFoundException {
        Client client = getClient(id);
        client.setFirstName(clientCreationDto.getFirstName());
        client.setLastName(clientCreationDto.getLastName());
        client.setUsername(clientCreationDto.getUsername());
        client.setEmail(clientCreationDto.getEmail());
        client.setAddress(clientCreationDto.getAddress());
        client.setCountry(clientCreationDto.getCountry());
        clientRepository.save(client);
        log.info("Updated Client with ID " + id);
    }
}
