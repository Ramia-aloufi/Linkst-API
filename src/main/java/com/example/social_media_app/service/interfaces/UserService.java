package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.exception.UserException;
import com.example.social_media_app.model.entity.User;

public interface UserService {
    // Define the methods that will be implemented in the UserServiceImp class
    User register(User user);

    User getUserById(UUID id) throws UserException;

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(UUID id, User user) throws UserException;

    void deleteUser(UUID id);

    User followUser(UUID userId, UUID followUserId) throws UserException;

    List<User> searchUsers(String query);

}
