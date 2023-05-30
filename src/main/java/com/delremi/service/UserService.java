package com.delremi.service;

import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.User;
import com.delremi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(String username, String password) {
        User result = userRepository.save(new User(null, username, passwordEncoder.encode(password)));
        log.info("Saved User with ID " + result.getId());
        return result;
    }

    public User getUser(int id) throws EntityNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, id);
        }
        return user;
    }
}
