package com.delremi.security;

import com.delremi.model.Role;
import com.delremi.model.User;
import com.delremi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    private static final String USERNAME = "user12345";
    private static final String PASSWORD = "password12345";
    private static final String ROLE_NAME = "TEST_ROLE_12345";
    private static final List<Role> ROLES = List.of(new Role(99999, ROLE_NAME));

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_shouldReturnCustomUserDetails() {
        // given
        var user = buildUser();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        // when
        var userDetails = customUserDetailsService.loadUserByUsername(USERNAME);

        // then
        assertThat(userDetails instanceof CustomUserDetails).isTrue();
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
        assertThat(userDetails.getAuthorities())
                .hasSize(1)
                .allMatch(authority -> authority.getAuthority().equals(ROLE_NAME));
    }

    @Test
    void loadUserByUsername_whenUserNotFound_shouldThrow() {
        // given
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        // when, then
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(USERNAME));
    }


    private User buildUser() {
        return User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .roles(ROLES)
                .build();
    }
}
