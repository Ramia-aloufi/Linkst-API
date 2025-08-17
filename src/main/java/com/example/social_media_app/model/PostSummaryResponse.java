
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
public class PostSummaryResponse {
    private UUID id;
    private String caption;
    private String content;
    private String media;
    private String type;
    private LocalDateTime createdAt;
    private int likeCount;
    private int commentCount;
    private boolean likedByCurrentUser;
    private UserSummary user;

    public PostSummaryResponse(UUID id, String caption, String content, String media, String type,
                           LocalDateTime createdAt, int likeCount, int commentCount,
                           boolean likedByCurrentUser, UUID userId, String fullName, String profilePicture) {
    this.id = id;
    this.caption = caption;
    this.content = content;
    this.media = media;
    this.type = type;
    this.createdAt = createdAt;
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.likedByCurrentUser = likedByCurrentUser;
    this.user = new UserSummary(userId, fullName, profilePicture);
}


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSummary {
        private UUID userId;
        private String fullName;
        private String profilePictureUrl;
    }
}
