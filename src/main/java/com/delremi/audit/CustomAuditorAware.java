package com.delremi.audit;

import com.delremi.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class CustomAuditorAware implements AuditorAware<String> {

    private static final String SYSTEM = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(SYSTEM);
        }
        return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUsername());
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new CustomAuditorAware();
    }
}
