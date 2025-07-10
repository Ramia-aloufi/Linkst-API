package com.example.social_media_app.model;

import java.util.UUID;

public interface CommentDto {
UUID getId();
    String getComment();

    UserInfo getUser();

    interface UserInfo {
        UUID getId();
        String getFullName(); // we'll use a @Transient getter in User
    }
}
