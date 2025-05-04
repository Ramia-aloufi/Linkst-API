package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Story;

public interface StoryService {
    Story createStory(Story story, UUID userId) throws Exception;

    List<Story> getAllStoriesByUserId(UUID userId) throws Exception;



}
