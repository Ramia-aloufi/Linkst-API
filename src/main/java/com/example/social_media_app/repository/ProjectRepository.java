package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_app.model.entity.Project;


public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p FROM Project p WHERE p.owner.id = :userId")
    List<Project> findByOwnerId(@Param("userId") UUID userId);
}
