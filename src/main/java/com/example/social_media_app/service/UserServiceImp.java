package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.User;
import com.example.social_media_app.repository.UserRepository;

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

    public User getUserById(UUID id) throws Exception {
        System.err.println("getUserById called with id: " + id);
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
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

    public User updateUser(UUID id, User user) throws Exception {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User followUser(UUID userId, UUID followUserId) throws Exception {
        User user1 = getUserById(userId);
        User user2 = getUserById(followUserId);
        if (!user1.getFollowers().isEmpty() || user1.getFollowers().contains(user2.getId())) {
            throw new Exception("You are already following this user");
        }

        user1.getFollowing().add(user2.getId());
        user2.getFollowers().add(user1.getId());
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

}
