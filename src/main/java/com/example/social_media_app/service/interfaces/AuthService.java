package com.example.social_media_app.service.interfaces;

import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.LoginRequest;

public interface AuthService {
    public String signUp(User user);

    public String login(LoginRequest user);

}
