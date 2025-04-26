package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.Post;
import com.example.social_media_app.service.PostService;
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;


    @GetMapping("/all")
    public List<Post> gePosts() {
        return postService.findAllPost();
    }
    @GetMapping("/save/{postId}/{userId}")
    public Post savePost(@PathVariable UUID postId, @PathVariable UUID userId) throws Exception {
        return postService.savedPost(postId, userId);
    }
    @GetMapping("/like/{postId}/{userId}")
    public Post likePost(@PathVariable UUID postId, @PathVariable UUID userId) throws Exception {
        return postService.likePost(postId, userId);
    }
    @GetMapping("/user/{userId}")
    public List<Post> getPostByUserId(@PathVariable UUID userId) {
        return postService.findPostByUserId(userId);
    }
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable UUID postId) {
        return postService.findPostById(postId);
    }
    @DeleteMapping("/delete/{postId}/{userId}")
    public String deletePost(@PathVariable UUID postId, @PathVariable UUID userId) {
        return postService.deletePost(postId, userId);
    }
    @PostMapping("/create/user/{userId}")
    public Post createPost(@PathVariable UUID userId, Post post) throws Exception {
        return postService.createNewPost(post, userId);
    }





}
