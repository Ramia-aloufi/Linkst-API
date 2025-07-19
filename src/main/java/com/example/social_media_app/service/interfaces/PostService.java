package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.social_media_app.model.PostLikeResponse;
import com.example.social_media_app.model.PostSummaryResponse;
import com.example.social_media_app.model.entity.Post;

public interface PostService {
    Post createNewPost(Post post, UUID userId) throws Exception;

    String deletePost(UUID postId, UUID userId);

    List<Post> findPostByUserId(UUID userId);

    Post findPostById(UUID postId);

    List<Post> findAllPost();

    Post savedPost(UUID postId, UUID userId) throws Exception;

    Post likePost(UUID postId, UUID userId) throws Exception;
    Page<List<PostSummaryResponse>> getPostSummaries(UUID userId,int page, int size) throws Exception;
    PostLikeResponse toggleLike(UUID postId, UUID currentUser)throws Exception;
}
