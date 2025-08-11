package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.Reels;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.ReelsRepository;
import com.example.social_media_app.service.interfaces.ReelsService;
import com.example.social_media_app.service.interfaces.UserService;

@Service
public class ReelsServiceImp implements ReelsService {

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private UserService userService;

    @Override
    public Reels createReels(String videoUrl, String title, UUID userId) throws Exception {
        // Get the user by ID
        User user = userService.getUserById(userId);

        // Create a new Reels object
        // and set the properties
        Reels newReels = new Reels();
        newReels.setUser(user);
        newReels.setVideoUrl(videoUrl);
        newReels.setTitle(title);

        // Save the new Reels object to the database
        reelsRepository.save(newReels);
        return newReels;

    }

    @Override
    public List<Reels> getAllReels() throws Exception {
        // Fetch all reels from the database
        List<Reels> reelsList = reelsRepository.findAll();
        return reelsList;
    }

    @Override
    public List<Reels> getReelsByUserId(UUID userId) throws Exception {
        // Fetch reels by user ID
        List<Reels> reelsList = reelsRepository.findByUserId(userId);
        return reelsList;
    }

}
