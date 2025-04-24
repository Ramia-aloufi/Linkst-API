package com.example.social_media_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional<User> findUserByEmail(String email);
   List<User> searchUsers(String query);
}
