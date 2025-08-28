package com.example.social_media_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.entity.Tool;

public interface ToolRepository extends JpaRepository<Tool, UUID> {

    Tool findByName(String name);

}
