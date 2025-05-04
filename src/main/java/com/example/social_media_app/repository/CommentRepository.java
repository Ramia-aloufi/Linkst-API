package com.example.social_media_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,UUID> {


}
