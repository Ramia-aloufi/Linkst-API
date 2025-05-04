package com.example.social_media_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.AuthResponse;
import com.example.social_media_app.model.response.LoginRequest;
import com.example.social_media_app.service.interfaces.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String auth = authService.login(loginRequest);

        return auth;
    }


    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody User user) {
        String token = authService.signUp(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMessage("User registered successfully");
        return authResponse;
    }

    

}
