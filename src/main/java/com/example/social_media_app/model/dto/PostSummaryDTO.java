package com.example.social_media_app.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PostSummaryDTO {
    UUID getId();
    String getCaption();
    String getContent();
    String getType();
    String getMedia();
    LocalDateTime getCreatedAt();

    Integer getLikeCount();
    Integer getCommentCount();

    UserDTO getUser();

    interface UserDTO {
        UUID getId();
        String getFullName(); 
    }
}
