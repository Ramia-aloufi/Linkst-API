package com.example.social_media_app.model;
import java.util.Optional;
import java.util.UUID;
public interface CommentDto {
    UUID getId();

    String getComment();

    UserInfo getUser();

    interface UserInfo {
        UUID getId();
        String getFullName();
        Optional<ProfileInfo> getProfile();

        
    }

    interface ProfileInfo {
        String getProfilePictureUrl();
    }
}
