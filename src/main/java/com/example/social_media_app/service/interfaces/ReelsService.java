package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Reels;

public interface ReelsService {
    public Reels createReels(Reels reels ,UUID userId) throws Exception;
    public List<Reels> getAllReels() throws Exception;
    public List<Reels> getReelsByUserId(UUID userId) throws Exception;
}
