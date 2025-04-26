package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.PostRepository;
import com.example.social_media_app.repository.UserRepository;
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
        newPost.setVideo(post.getVideo());
        newPost.setImage(post.getImage());
        return postRepository.save(newPost);
    }

    public String deletePost(UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getUser().getId() != userId) {
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
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public Post likePost(UUID postId, UUID userId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new Exception("User not found");
        }
        if (post.getUser().getId() == userId) {
            throw new Exception("You cannot like your own post");
        }
        if (post.getLikes().contains(user)) {
            post.getLikes().remove(user);
        } else {
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    public Post savedPost(UUID postId, UUID userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new Exception("User not found");
        }
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
        } else {
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

}
