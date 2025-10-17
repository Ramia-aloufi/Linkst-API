package com.example.social_media_app.model.dto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private UUID id;
    private String lastMessage;
    private UUID userId;
    private String fullName;
    private String profileImage;
}

