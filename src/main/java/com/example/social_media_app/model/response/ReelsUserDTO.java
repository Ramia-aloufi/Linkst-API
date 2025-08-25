package com.example.social_media_app.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReelsUserDTO {
    UUID getId();

    String getTitle();

    String getVideoUrl();

    UserInfo getUser();

    LocalDateTime getCreatedAt();

    public interface UserInfo {
    UUID getId();

    String getFullName();

    ProfileInfo getProfile();

}

public interface ProfileInfo {
    String getProfilePictureUrl();
}

}

