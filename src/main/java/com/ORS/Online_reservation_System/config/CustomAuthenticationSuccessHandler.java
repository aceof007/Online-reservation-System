package com.ORS.Online_reservation_System.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/Dashboard");
                return;
            } else if (role.equals("ROLE_USER") || role.equals("ROLE_CUSTOMER")) {
                // Redirect to originally requested URL or home
                String redirectUrl = null;
                if (redirectUrl != null) {
                    response.sendRedirect(redirectUrl);
                } else {
                    response.sendRedirect("/ProfileManegement");
                }
                return;
            }
        }

        // Default fallback
        response.sendRedirect("/");
    }
}

