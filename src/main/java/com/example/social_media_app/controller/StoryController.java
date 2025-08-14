package com.example.social_media_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.model.entity.Story;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.StoryService;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/create")
    public Story createStory(@RequestParam("media") MultipartFile file,@RequestParam("caption") String caption, Authentication auth) {
        try {
            var uploadResult = cloudinaryService.uploadFile(file,"stories");

            String mediaUrl = uploadResult.get("url").toString();
            String mediaType = uploadResult.get("type").toString();

            Story story = new Story();
            story.setMedia(mediaUrl);
            story.setCaption(caption);
            story.setMediaType(mediaType);

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
