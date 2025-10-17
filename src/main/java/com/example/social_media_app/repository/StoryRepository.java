package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.social_media_app.model.entity.Story;

public interface StoryRepository extends JpaRepository<Story, UUID> {

    public List<Story> findByUserId(UUID userId);
    @Query("SELECT s FROM Story s ORDER BY s.createdAt DESC")
    public List<Story> findLatestStories();






}
