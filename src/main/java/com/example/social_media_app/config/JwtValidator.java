package com.example.social_media_app.config;

import java.io.IOException;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.social_media_app.model.CustomUserDetails;
import com.example.social_media_app.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class JwtValidator extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JwtValidator(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        System.err.println("JWT: " + jwt);

        if (jwt != null) {
            try {
                String email = JwtProvider.getEmailFromToken(jwt);
                System.err.println("JWT: email " + email);
                // Load user details from your service (based on the user ID)
                CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                System.err.println("JWT: userDetails " + userDetails);
                // Set the authentication in the security context
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                e.printStackTrace();
                throw new BadCredentialsException("Invalid JWT token provided");
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);

    }
}