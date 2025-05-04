package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.entity.Reels;

public interface ReelsRepository extends JpaRepository<Reels, UUID> {
    public List<Reels> findByUserId(UUID userId);

}
