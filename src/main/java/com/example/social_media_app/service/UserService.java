package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.User;

public interface UserService {
    // Define the methods that will be implemented in the UserServiceImp class
    User register(User user);
    User getUserById(UUID id) throws Exception;
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(UUID id, User user)  throws Exception ;
    void deleteUser(UUID id);
    User followUser(UUID userId, UUID followUserId) throws Exception;
    List<User> searchUsers(String query);
    User getUserFromToken(String token) throws Exception;



}
