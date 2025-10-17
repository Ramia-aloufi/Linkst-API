package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.social_media_app.model.dto.CommentDto;
import com.example.social_media_app.model.entity.Comment;
import com.example.social_media_app.model.entity.Post;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.CommentRepository;
import com.example.social_media_app.repository.PostRepository;
import com.example.social_media_app.service.interfaces.CommentService;
import com.example.social_media_app.service.interfaces.PostService;
import com.example.social_media_app.service.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(CommentService.class);

    @Transactional
    public Comment createComment(String comment, UUID postId, UUID userId) throws Exception {
        logger.info("Comment created : {} ", comment);

        User user = userService.getUserById(userId);
        Post post = postService.findPostById(postId);

        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setPost(post);
        newComment.setUser(user);
        Comment savedComment = commentRepository.save(newComment);

        logger.info("Comment created with ID: {} for Post ID: {} by User ID: {}", savedComment.getId(), postId, userId);
        return savedComment;
    }

    public Comment getCommentById(UUID commentId) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found with ID: " + commentId));
        return comment;
    }

    public Comment likeComment(UUID commentId, UUID userId) throws Exception {
        Comment comment = getCommentById(commentId);
        User user = userService.getUserById(userId);
        if (comment.getLikes().contains(user)) {
            comment.getLikes().remove(user);
            logger.info("User ID: {} unliked Comment ID: {}", userId, commentId);
        } else {
            comment.getLikes().add(user);
            logger.info("User ID: {} liked Comment ID: {}", userId, commentId);
        }
        commentRepository.save(comment);
        return comment;

    }

    public List<CommentDto> getCommentByPostId(UUID postId) throws Exception {
        System.out.println("Fetching comments for Post ID: " + postId);
        List<CommentDto> commentDtos = commentRepository.findByPostId(postId);
        System.out.println("Fetched comments: " + commentDtos.stream().map(dto -> {
            return "Comment ID: " + dto.getId() + ", Comment: " + dto.getComment() + ", User: " + dto.getUser();
        }).toList());
        return commentDtos;
    }

    public String deleteComment(UUID id) throws Exception {
        commentRepository.deleteById(id);
        return "Commit deleted Successfully";
    }
    

}
