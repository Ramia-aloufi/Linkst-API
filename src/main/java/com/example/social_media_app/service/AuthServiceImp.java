package com.example.social_media_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.social_media_app.config.JwtProvider;
import com.example.social_media_app.model.LoginRequest;
import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.UserRepository;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public String signUp(User user) {
        // Check if the user already exists
         if(userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
         
        // Create a new user
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setGender(user.getGender());

        // Save the user to the database
        User savedUser = userRepository.save(newUser);

        // Create an authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());

        // Generate a JWT token for the user
        String token = JwtProvider.generateToken(authentication);
        return token;
    }

    public String login(LoginRequest loginRequest) {
        // Authenticate the user
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        if (userDetails == null || !passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        String token = JwtProvider.generateToken(authentication);

        return token;

    }
}
