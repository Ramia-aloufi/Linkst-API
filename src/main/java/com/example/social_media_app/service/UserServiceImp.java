package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.exception.UserException;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.UserLatestStoryDTO;
import com.example.social_media_app.repository.UserRepository;
import com.example.social_media_app.service.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    public User register(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);
        return savedUser;

    }

    public User getUserById(UUID id) throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User updateUser(UUID id, User user) throws UserException {
    User userUpdated = ((User) user); // unwrap

        User existingUser = getUserById(id);
        if (userUpdated.getFirstName() != null) {
        existingUser.setFirstName(userUpdated.getFirstName());
        } 
        if (userUpdated.getLastName() != null) {
        existingUser.setLastName(userUpdated.getLastName());
        }
        if (userUpdated.getEmail() != null) {
        existingUser.setEmail(userUpdated.getEmail());
        }
        if (userUpdated.getPassword() != null) {
        existingUser.setPassword(userUpdated.getPassword());
        }
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public User followUser(UUID userId, UUID followUserId) throws UserException {
        User me = getUserById(userId);
        User followingUser = getUserById(followUserId);
        if (!followingUser.getFollowers().isEmpty() || followingUser.getFollowers().contains(me.getId())) {
        me.getFollowing().remove(followingUser.getId());
        followingUser.getFollowers().remove(me.getId());
        }else{
        me.getFollowing().add(followingUser.getId());
        followingUser.getFollowers().add(me.getId());
        }
        userRepository.save(me);
        userRepository.save(followingUser);
        return me;
    }

    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

    public List<UserLatestStoryDTO> getUsersWithLatestStory() {
        List<User> users = userRepository.findUsersWhoLatestAddStory();
        List<UserLatestStoryDTO> userLatestStoryDTO = users.stream()
                .map(user -> new UserLatestStoryDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                       (user.getProfile() != null) ? user.getProfile().getProfilePictureUrl() : "",
                        user.getStories()
                ))
                .toList();
        return userLatestStoryDTO;
    }

    @Override
    public User getUserByFullName(String fullName) throws UserException {
        return userRepository.findByFullName(fullName).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User getUserByPostId(UUID postId) throws UserException {
        return userRepository.findUserByPostId(postId).orElseThrow(() -> new UserException("User not found"));
    }
}
