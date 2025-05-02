package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.User;
import com.example.social_media_app.service.PostService;
import com.example.social_media_app.service.UserService;
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;


    @GetMapping("/all")
    public List<Post> gePosts() {
        return postService.findAllPost();
    }
    @GetMapping("/save/{postId}")
    public Post savePost(@PathVariable UUID postId,@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.getUserFromToken(token);
        return postService.savedPost(postId, user.getId());
    }
    @GetMapping("/like/{postId}")
    public Post likePost(@RequestHeader("Authorization") String token,@PathVariable UUID postId) throws Exception {
        User user = userService.getUserFromToken(token);
        return postService.likePost(postId, user.getId());
    }
    @GetMapping("/user")
    public List<Post> getPostByUserId(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.getUserFromToken(token);
        return postService.findPostByUserId(user.getId());
    }
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable UUID postId) {
        return postService.findPostById(postId);
    }
    @DeleteMapping("/delete/{postId}")
    public String deletePost(@RequestHeader("Authorization") String token, @PathVariable UUID postId) throws Exception {
        User user = userService.getUserFromToken(token);
        return postService.deletePost(postId, user.getId());
    }
    @PostMapping("/create")
    public Post createPost(@RequestHeader("Authorization") String token, @RequestBody Post post) throws Exception {
        User user = userService.getUserFromToken(token);
        return postService.createNewPost(post, user.getId());
    }




 
}
