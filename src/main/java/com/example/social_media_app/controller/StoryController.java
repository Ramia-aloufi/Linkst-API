package com.example.social_media_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.entity.Story;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.interfaces.StoryService;

@RestController
@RequestMapping("/story")
public class StoryController {


    @Autowired
    private StoryService storyService;


    @PostMapping("/create")
    public Story createStory(@RequestBody Story story,Authentication auth) {
        try {
            CustomUserDetails userId = (CustomUserDetails) auth.getPrincipal();
            return storyService.createStory(story, userId.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating story: " + e.getMessage());
        }
    }
    @GetMapping("/user")
    public List<Story> getAllStoriesByUserId(Authentication auth) {
        try {
            CustomUserDetails userId = (CustomUserDetails) auth.getPrincipal();
            return storyService.getAllStoriesByUserId(userId.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching stories: " + e.getMessage());
        }
    }
}
