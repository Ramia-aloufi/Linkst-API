package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.example.social_media_app.model.PostSummaryResponse;
import com.example.social_media_app.model.PostSummaryResponse.UserSummary;
import com.example.social_media_app.model.entity.Post;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.model.response.PaginateResponse;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.PostService;
import com.example.social_media_app.service.interfaces.UserService;

import jakarta.servlet.http.HttpServletRequest;

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
    public PostSummaryResponse getPostById(@PathVariable UUID postId, Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Post post = postService.findPostById(postId);
        User user = userService.getUserByPostId(postId);
        User me = userService.getUserById(userDetails.getId());

        UserSummary userSummary = new UserSummary();
        userSummary.setUserId(user.getId());
        userSummary.setFullName(user.getFullName());
        userSummary.setProfilePictureUrl(user.getProfile() != null ? user.getProfile().getProfilePictureUrl() : null);


        PostSummaryResponse postSummary = new PostSummaryResponse();
        postSummary.setId(post.getId());
        postSummary.setCaption(post.getCaption());
        postSummary.setMedia(post.getMedia());
        postSummary.setCommentCount(post.getComments().size());
        postSummary.setType(post.getType());
        postSummary.setLikeCount(post.getLikes().size());
        postSummary.setLikedByCurrentUser(post.getLikes().contains(me) || false);
        postSummary.setUser(userSummary);
        postSummary.setContent(post.getContent());
        postSummary.setCreatedAt(post.getCreatedAt());

        return postSummary;
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(Authentication auth, @PathVariable UUID postId) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return postService.deletePost(postId, userDetails.getId());
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")

    public Post createPost(Authentication auth, @RequestParam(value = "media", required = false) MultipartFile file,
            @RequestParam("content") String content) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Post post = new Post();
        post.setContent(content);
        post.setCaption(null);
        if (file != null && !file.isEmpty()) {
            var uploadResult = cloudinaryService.uploadFile(file, "posts");
            post.setMedia(uploadResult.get("url"));
            post.setType(uploadResult.get("type"));
        } else {
            post.setMedia(null);
            post.setType(null);
            post.setCaption(null);

        }

        return postService.createNewPost(post, userDetails.getId());
    }

    @GetMapping("/summaries")
    public PaginateResponse<List<PostSummaryResponse>> getPostSummaries(Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request)
            throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Page<List<PostSummaryResponse>> posts = postService.getPostSummaries(userDetails.getId(), page, size);

        String baseURL = request.getRequestURL().toString();
        String hasNext = posts.hasNext() ? String.format("%s?page=%d&size=%d", baseURL, page + 1, size) : null;
        String hasPerv = posts.hasPrevious() ? String.format("%s?page=%d&size=%d", baseURL, page - 1, size) : null;

        var paginate = new PaginateResponse<List<PostSummaryResponse>>(
                posts.getContent(),
                posts.getNumber(),
                posts.getTotalPages(),
                posts.getTotalElements(),
                posts.hasNext(),
                posts.hasPrevious(),
                hasNext,
                hasPerv);

        return paginate;
    }

}
