package com.delremi;

import com.delremi.dto.CityEditDto;
import com.delremi.security.CustomUserDetailsService;
import com.delremi.service.CityService;
import com.delremi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static com.delremi.security.Roles.EDITOR;
import static com.delremi.security.Roles.VIEWER;

@SpringBootApplication
@Slf4j
public class SpringThymeleafDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringThymeleafDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, CityService cityService) {
        return args -> {
            populateUsers(userService);
            populateCities(cityService);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    private void populateUsers(UserService userService) {
        userService.saveUser("del", "del", List.of(VIEWER));
        userService.saveUser("del1", "del1", List.of(VIEWER, EDITOR));
    }

    private void populateCities(CityService cityService) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(ResourceUtils.getFile("classpath:cities.csv"))) {
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                var split = line.split(",");
                try {
                    cityService.saveCity(CityEditDto.of(split[1], split[2]));
                } catch (Exception e) {
                    log.error("Failed to create city from string '{}'. Skipping line.", line);
                }
            }
        }
    }
}
