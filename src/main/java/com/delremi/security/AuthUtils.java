package com.delremi.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static int getAuthenticatedUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUserId();
    }
}
