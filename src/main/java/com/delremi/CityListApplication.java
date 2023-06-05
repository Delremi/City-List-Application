package com.delremi;

import com.delremi.dto.CityEditDto;
import com.delremi.model.Role;
import com.delremi.repository.RoleRepository;
import com.delremi.service.CityService;
import com.delremi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static com.delremi.security.Roles.EDITOR;
import static com.delremi.security.Roles.VIEWER;

@SpringBootApplication
@Slf4j
public class CityListApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityListApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, CityService cityService, RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new Role(null, "ROLE_EDITOR"));
            roleRepository.save(new Role(null, "ROLE_VIEWER"));
            populateUsers(userService);
            populateCities(cityService);
        };
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
