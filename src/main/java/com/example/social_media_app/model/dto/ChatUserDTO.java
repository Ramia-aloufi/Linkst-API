package com.example.social_media_app.model.dto;
import java.util.UUID;

public interface ChatUserDTO {
    UUID getId();
    String getFullName();
    String getProfile();
}