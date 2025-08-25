package com.example.social_media_app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.PostLikeResponse;
import com.example.social_media_app.model.PostSummaryResponse;
import com.example.social_media_app.model.entity.Post;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.PostRepository;
import com.example.social_media_app.repository.UserRepository;
import com.example.social_media_app.service.interfaces.PostService;
import com.example.social_media_app.service.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public Post createNewPost(Post post, UUID userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new Exception("User not found");
        }
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setContent(post.getContent());
        newPost.setUser(user);
        newPost.setMedia(post.getMedia());
        newPost.setType(post.getType());
        Post savedPost = postRepository.save(newPost);
        System.out.println(savedPost);
        return savedPost;
    }

    public String deletePost(UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }
        postRepository.delete(post);
        return "Post deleted successfully";

    }

    public List<Post> findPostByUserId(UUID userId) {

        List<Post> posts = postRepository.findPostByUserId(userId);
        if (posts.isEmpty()) {
            throw new RuntimeException("No posts found for this user");
        }
        return posts;
    }

    public Post findPostById(UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        return post;

    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public Post likePost(UUID postId, UUID userId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userService.getUserById(userId);
        if (user.equals(null)) {
            throw new Exception("User not found");
        }
        if (post.getUser().getId().equals(userId)) {
            throw new Exception("You cannot like your own post");
        }
        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
        } else {
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    @Transactional
    public Post savedPost(UUID postId, UUID userId) throws Exception {
        User user = userService.getUserById(userId);
        Post post = findPostById(postId);
        System.out.println("post: " + post.getId());
        if (post.getUser().getId().equals(userId)) {
            throw new Exception("You cannot save your own post");
        }

        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
        } else {
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }
    @Override
    public Page<List<PostSummaryResponse>> getPostSummaries(UUID userId,int page, int size) throws Exception {
                User user = userService.getUserById(userId);
               Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());


        return postRepository.findAllPostSummaries(user,pageable);
    }
    @Transactional
    public PostLikeResponse toggleLike(UUID postId, UUID currentUser) throws Exception {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new RuntimeException("Post not found"));
   User user = userService.getUserById(currentUser);


    boolean liked;
    if (post.getLikes().contains(user)) {
        post.getLikes().remove(user);
        liked = false;
    } else {
        post.getLikes().add(user);
        liked = true;
    }
    return new PostLikeResponse(post.getId(), liked, post.getLikes().size());
}
}
