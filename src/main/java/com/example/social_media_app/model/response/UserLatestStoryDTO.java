package com.example.social_media_app.model.response;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Story;



public record UserLatestStoryDTO(
    UUID userId,
    String firstName,
    String lastName,
    String profilePictureUrl,
    List<Story> stories
) {


}


