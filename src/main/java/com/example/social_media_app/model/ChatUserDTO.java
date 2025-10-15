package com.example.social_media_app.model;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface ChatUserDTO {
    UUID getId();
    String getFullName();       // or getFirstName() + getLastName()
    String getProfile();
}