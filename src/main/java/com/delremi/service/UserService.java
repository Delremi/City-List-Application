package com.delremi.service;

import com.delremi.model.CustomUser;
import com.delremi.repository.RoleRepository;
import com.delremi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(String username, String password, List<String> roles) {
        var userRoles = roleRepository.findAllByRoleIn(roles);
        var result = userRepository.save(new CustomUser(null, username, passwordEncoder.encode(password), userRoles));
        log.info("Saved User with ID " + result.getId());
    }
}
