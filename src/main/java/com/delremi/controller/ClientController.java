package com.delremi.controller;

import com.delremi.dto.ClientCreationDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.Client;
import com.delremi.service.ClientService;
import com.delremi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String showClientList(Model model) throws EntityNotFoundException {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, ClientCreationDto clientCreationDto) {
        model.addAttribute("countries", countryService.getCountries());
        return "add-client";
    }

    @PostMapping("/add")
    public String addClient(@Valid ClientCreationDto clientCreationDto, BindingResult bindingResult, Model model) throws EntityNotFoundException {
        if (bindingResult.hasErrors()) {
            return showAddForm(model, clientCreationDto);
        }
        clientService.saveClient(clientCreationDto);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, ClientCreationDto clientCreationDto) throws EntityNotFoundException {
        Client client = clientService.getClient(id);
        clientCreationDto = new ClientCreationDto(client.getFirstName(), client.getLastName(), client.getUsername(), client.getEmail(), client.getAddress(), client.getCountry());
        model.addAttribute("clientCreationDto", clientCreationDto);
        model.addAttribute("clientId", id);
        model.addAttribute("countries", countryService.getCountries());
        return "edit-client";
    }

    private String reloadEditForm(int id, Model model, ClientCreationDto clientCreationDto) {
        model.addAttribute("clientCreationDto", clientCreationDto);
        model.addAttribute("clientId", id);
        model.addAttribute("countries", countryService.getCountries());
        return "edit-client";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(@PathVariable int id, @Valid ClientCreationDto clientCreationDto, BindingResult bindingResult, Model model) throws EntityNotFoundException {
        if (bindingResult.hasErrors()) {
            return reloadEditForm(id, model, clientCreationDto);
        }
        clientService.updateClient(id, clientCreationDto);
        return "redirect:/";
    }
}
