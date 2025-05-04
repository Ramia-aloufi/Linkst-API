package com.example.social_media_app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.entity.Comment;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.interfaces.CommentService;
import com.example.social_media_app.service.interfaces.PostService;
import com.example.social_media_app.service.interfaces.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;


    @PostMapping("/create/post/{postId}")
    public Comment createComment(Authentication auth,@PathVariable UUID postId,@RequestBody String comment) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return commentService.createComment(comment, postId, userDetails.getId());
    }
    @GetMapping("/{commentId}")
    public Comment getComment(@PathVariable UUID commentId) throws Exception {
        return commentService.getCommentById(commentId);
    }
    @PutMapping("/update/{commentId}")
    public Comment updateComment(Authentication auth,@PathVariable UUID commentId) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return commentService.likeComment(commentId, userDetails.getId());
    }

}
