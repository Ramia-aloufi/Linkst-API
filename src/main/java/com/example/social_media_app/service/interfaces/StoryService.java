package com.example.social_media_app.service.interfaces;

import java.util.UUID;

import com.example.social_media_app.model.entity.Story;

public interface StoryService {
    Story createStory(Story story, UUID userId) throws Exception;

}
