package com.example.social_media_app.config;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.social_media_app.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/login", "/actuator/**").permitAll()// whitelist

                        .anyRequest().authenticated() // Protect all other routes
                )
                .addFilterBefore(new JwtValidator(customUserDetailsService), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session
                )
                .cors(cors -> cors.configurationSource(corseConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corseConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173")); // Allow all
                                                                                                           // origins
                config.setAllowedMethods(Collections.singletonList("*")); // Allow all methods (GET, POST, etc.)
                config.setAllowedHeaders(Collections.singletonList("*")); // Allow specific headers
                config.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
                config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type")); // Expose specific headers
                config.setMaxAge(3600L); // Set max age for preflight requests
                return config;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
