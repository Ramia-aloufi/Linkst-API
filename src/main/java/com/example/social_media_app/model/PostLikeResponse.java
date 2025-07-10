package com.example.social_media_app.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeResponse {
      private UUID id;
    private boolean likedByCurrentUser;
    private int likeCount;
}
