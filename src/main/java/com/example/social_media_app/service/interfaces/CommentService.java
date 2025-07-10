package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.CommentDto;
import com.example.social_media_app.model.entity.Comment;

public interface CommentService {
    public Comment createComment(String comment, UUID postId, UUID userId) throws Exception;

    // public String deleteComment(String commentId, String postId, String userId)
    // throws Exception;
    // public String updateComment(String commentId, String postId, String userId,
    // String comment) throws Exception;
    public List<CommentDto> getCommentByPostId(UUID postId) throws Exception;

    // public String getCommentByUserId(String userId) throws Exception;
    // public String getAllComments() throws Exception;
    public Comment getCommentById(UUID commentId) throws Exception;

    public Comment likeComment(UUID commentId, UUID userId) throws Exception;

}
