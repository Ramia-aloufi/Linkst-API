package com.example.social_media_app.model.dto;

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
public class StoryDto {
    private UUID id;
    private String media;
    private String caption;
    private String mediaType;
    private LocalDateTime createdAt;


}
