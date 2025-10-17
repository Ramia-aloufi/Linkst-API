package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.dto.UserDto;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.UserLatestStoryDTO;
import com.example.social_media_app.utils.exception.UserException;



public interface UserService {
    User register(User user);

    User getUserById(UUID id) throws UserException;

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(UUID id, User user) throws UserException;

    void deleteUser(UUID id);

    User followUser(UUID userId, UUID followUserId) throws UserException;

    List<UserDto> searchUsers(String query);

    List<UserLatestStoryDTO> getUsersWithLatestStory();
    User getUserByFullName(String fullName) throws UserException;
    User getUserByPostId(UUID postId) throws UserException;

}
