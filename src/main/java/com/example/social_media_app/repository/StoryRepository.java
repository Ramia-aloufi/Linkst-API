package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.entity.Story;

public interface StoryRepository extends JpaRepository<Story, UUID> {

    public List<Story> findByUserId(UUID userId);

    public List<Story> findTop5ByOrderByCreatedAtDesc();






}
