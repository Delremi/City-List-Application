package com.delremi.service;

import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.Role;
import com.delremi.model.User;
import com.delremi.repository.RoleRepository;
import com.delremi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User saveUser(String username, String password, List<String> roles) {
        List<Role> userRoles = roleRepository.findAllByRoleIn(roles);
        User result = userRepository.save(new User(null, username, passwordEncoder.encode(password), userRoles));
        log.info("Saved User with ID " + result.getId());
        log.info(result.toString());
        return result;
    }

    @Transactional(readOnly = true)
    public User getUser(int id) throws EntityNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, id);
        }
        return user;
    }
}
