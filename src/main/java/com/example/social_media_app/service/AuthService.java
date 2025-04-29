package com.example.social_media_app.service;

import com.example.social_media_app.model.LoginRequest;
import com.example.social_media_app.model.User;

public interface AuthService {
 public String signUp(User user);
 public String login(LoginRequest user);

}
