package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_app.model.PostSummaryResponse;
import com.example.social_media_app.model.entity.Post;
import com.example.social_media_app.model.entity.User;

public interface PostRepository extends JpaRepository<Post, UUID> {
@Query("select p from Post p where p.user.id=:userId")
List<Post> findPostByUserId (UUID userId);
// @Query("""
//     SELECT p.id AS id,
//            p.caption AS caption,
//            p.content AS content,
//            p.media AS media,
//            p.type AS type,
//            p.createdAt AS createdAt,
//            SIZE(p.likes) AS likeCount,
//            SIZE(p.comments) AS commentCount,
//            p.user AS user
//     FROM Post p
//     """)
@Query("""
    SELECT new com.example.social_media_app.model.PostSummaryResponse(
        p.id,
        p.caption,
        p.content,
        p.media,
        p.type,
        p.createdAt,
        SIZE(p.likes),
        SIZE(p.comments),
        CASE WHEN :user MEMBER OF p.likes THEN true ELSE false END,
        p.user.id,
        CONCAT(p.user.firstName, ' ', p.user.lastName)
    )
    FROM Post p
    ORDER BY p.createdAt DESC
""")
List<PostSummaryResponse> findAllPostSummaries(@Param("user") User currentUser);



}
