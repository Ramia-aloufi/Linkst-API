package com.example.social_media_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_app.model.dto.UserDto;
import com.example.social_media_app.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findUserByEmail(String email);
//    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%")
@Query("""
    SELECT new com.example.social_media_app.model.dto.UserDto(
        u.id,
        CONCAT(u.firstName, ' ', u.lastName),
        u.profile.profilePictureUrl
    )
    FROM User u
    WHERE u.firstName LIKE %:query%
       OR u.lastName LIKE %:query%
       OR u.email LIKE %:query%
""")
   List<UserDto> searchUsers(String query);

@Query("""
    SELECT u
    FROM User u
    JOIN u.stories s
    GROUP BY u
    ORDER BY MAX(s.createdAt) DESC
    LIMIT 5
""")
    List<User> findUsersWhoLatestAddStory();

@Query("SELECT u FROM User u WHERE CONCAT(u.firstName, '_', u.lastName) = :fullName")
Optional<User> findByFullName(@Param("fullName") String fullName);



@Query("SELECT p.user FROM Post p WHERE p.id = :postId")
Optional<User> findUserByPostId(@Param("postId") UUID postId);







}
