package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.model.PostLikeResponse;
import com.example.social_media_app.model.PostSummaryDTO;
import com.example.social_media_app.model.PostSummaryResponse;
import com.example.social_media_app.model.entity.Post;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.PostService;
import com.example.social_media_app.service.interfaces.UserService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<Post> gePosts() {
        return postService.findAllPost();
    }

    @GetMapping("/save/{postId}")
    public Post savePost(@PathVariable UUID postId, Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.savedPost(postId, userDetails.getId());
    }

    @GetMapping("/like/{postId}")
    public PostLikeResponse likePost(Authentication auth, @PathVariable UUID postId) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.toggleLike(postId, userDetails.getId());
    }

    @GetMapping("/user")
    public List<Post> getPostByUserId(Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.findPostByUserId(userDetails.getId());
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable UUID postId) {
        return postService.findPostById(postId);
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(Authentication auth, @PathVariable UUID postId) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.deletePost(postId, userDetails.getId());
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")

    public Post createPost(Authentication auth, @RequestParam("media") MultipartFile file,
            @RequestParam("caption") String caption, @RequestParam("content") String content) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        var uploadResult = cloudinaryService.uploadFile(file);
        Post post = new Post();
        post.setMedia(uploadResult.get("url"));
        post.setType(uploadResult.get("type"));
        post.setCaption(caption);
        post.setContent(content);
        return postService.createNewPost(post, userDetails.getId());
    }
    @GetMapping("/summaries")
    public List<PostSummaryResponse> getPostSummaries(Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.getPostSummaries(userDetails.getId());
    }

}
