package com.example.social_media_app.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private UUID id;
    private String content;
    private String image;
    private UserDTO user;
    private LocalDateTime timestamp;
}


