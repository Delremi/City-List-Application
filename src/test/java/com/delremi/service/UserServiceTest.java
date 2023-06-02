package com.delremi.service;

import com.delremi.model.Role;
import com.delremi.model.User;
import com.delremi.repository.RoleRepository;
import com.delremi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USERNAME = "username12345";
    private static final String PASSWORD = "password12345";
    private static final String ENCODED_PASSWORD = "encoded-password12345";
    private static final String USER_ROLE = "ROLE_TEST_USER_12345";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void saveUser_shouldSaveUser() {
        // given
        var userRole = new Role(-9999, USER_ROLE);
        when(roleRepository.findAllByRoleIn(List.of(USER_ROLE))).thenReturn(List.of(userRole));
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(new User());

        // when
        userService.saveUser(USERNAME, PASSWORD, List.of(USER_ROLE));

        // then
        assertThat(userArgumentCaptor.getValue().getId()).isNull();
        assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo(USERNAME);
        assertThat(userArgumentCaptor.getValue().getPassword()).isEqualTo(ENCODED_PASSWORD);
        assertThat(userArgumentCaptor.getValue().getRoles()).containsExactly(userRole);
    }
}
