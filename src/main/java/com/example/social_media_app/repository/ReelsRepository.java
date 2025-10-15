package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_app.model.entity.Reels;
import com.example.social_media_app.model.response.ReelsUserDTO;

public interface ReelsRepository extends JpaRepository<Reels, UUID> {
    @Query("""
                SELECT r FROM Reels r
                WHERE r.user.id = :userId
                ORDER BY r.createdAt DESC
            """)
    List<ReelsUserDTO> findByUserId(@Param("userId") UUID userId);

    @Query("""
                SELECT r FROM Reels r
                ORDER BY r.createdAt DESC
            """)
    List<ReelsUserDTO> getAllReelsSorted();

}
