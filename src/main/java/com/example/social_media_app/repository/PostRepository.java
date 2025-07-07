package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.social_media_app.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {
@Query("select p from Post p where p.user.id=:userId")
List<Post> findPostByUserId (UUID userId);


}
