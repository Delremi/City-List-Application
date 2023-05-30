package com.delremi;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.security.CustomUserDetailsService;
import com.delremi.service.CityService;
import com.delremi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
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
        userService.saveUser("del", "del");
    }

    private void populateCities(CityService cityService) throws EntityNotFoundException {
        cityService.saveCity(CityEditDto.of("Tokyo", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg"));
        cityService.saveCity(CityEditDto.of("Jakarta", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jakarta_Pictures-1.jpg/327px-Jakarta_Pictures-1.jpg"));
        cityService.saveCity(CityEditDto.of("Dehli", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"));
        cityService.saveCity(CityEditDto.of("Dehli", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"));
        cityService.saveCity(CityEditDto.of("Dehli", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"));
        cityService.saveCity(CityEditDto.of("Dehli", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"));
        cityService.saveCity(CityEditDto.of("Dehli", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png"));
    }
}
