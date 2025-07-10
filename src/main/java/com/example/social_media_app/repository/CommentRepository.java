package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.social_media_app.model.CommentDto;
import com.example.social_media_app.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,UUID> {
@Query("""
    SELECT c FROM Comment c
    WHERE c.post.id = :postId
    ORDER BY c.createdAt ASC
    """)
List<CommentDto> findByPostId(UUID postId);

}
