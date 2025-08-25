package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Reels;
import com.example.social_media_app.model.response.ReelsUserDTO;

public interface ReelsService {
    public Reels createReels(String videoUrl, String title, UUID userId) throws Exception;

    public List<Reels> getAllReels() throws Exception;

    public List<ReelsUserDTO> getReelsByUserId(UUID userId) throws Exception;
}
