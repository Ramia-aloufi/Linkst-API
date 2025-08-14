package com.example.social_media_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.social_media_app.model.entity.Profile;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    Profile findByUserId(UUID userId);
}
