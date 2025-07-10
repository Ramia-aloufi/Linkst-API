package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.Story;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.StoryRepository;
import com.example.social_media_app.service.interfaces.StoryService;
import com.example.social_media_app.service.interfaces.UserService;

@Service
public class StoryServiceImp implements StoryService {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;

    public Story createStory(Story story, UUID userId) throws Exception {
        User user = userService.getUserById(userId);
        Story newStory = new Story();
        newStory.setCaption(story.getCaption());
        newStory.setImageUrl(story.getImageUrl());
        newStory.setUser(user);
        storyRepository.save(newStory);
        return newStory;

    }

    public List<Story> getAllStoriesByUserId(UUID userId) throws Exception {
        return storyRepository.findByUserId(userId);
    }

}
