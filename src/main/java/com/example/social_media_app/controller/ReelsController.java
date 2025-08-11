package com.example.social_media_app.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.model.entity.Reels;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.ReelsService;

@RestController
@RequestMapping("/reels")
public class ReelsController {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/create")
    public Reels createReels(Authentication auth, @RequestParam("videoUrl") MultipartFile file,@RequestParam("title") String title) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                var uploadResult = cloudinaryService.uploadFile(file,"reels");
                String videoUrl = uploadResult.get("url").toString();
        Reels newReels = reelsService.createReels(videoUrl, title, userDetails.getId());
        return newReels;
    }

    @GetMapping("/getAll")
    public List<Reels> getAllReels() throws Exception {
        List<Reels> reelsList = reelsService.getAllReels();
        return reelsList;
    }

    @GetMapping("/user")
    public List<Reels> getReelsByUserId(Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        List<Reels> reelsList = reelsService.getReelsByUserId(userDetails.getId());
        return reelsList;
    }
}
