package com.example.utils;



import com.example.config.details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {

        public static CustomUserDetails getCurrentUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName(); // username
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return (CustomUserDetails) authentication.getPrincipal();
        }
    }


