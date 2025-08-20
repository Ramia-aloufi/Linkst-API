package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.model.response.UserLatestStoryDTO;
import com.example.social_media_app.service.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) throws Exception {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) throws Exception {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("query") String query) {
        return userService.searchUsers(query);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping()
    public User updateUser(Authentication auth, @RequestBody @Valid User user) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userService.updateUser(userDetails.getId(), user);
    }

    @PutMapping("/follow/{userId}")
    public User followUser(Authentication auth, @PathVariable UUID userId) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userService.followUser(userDetails.getId(), userId);
    }

    @PostMapping()
    public User createUser(@RequestBody @Valid User user) {
        return userService.register(user);
    }

    @GetMapping("/profile")
    public Object getUserProfile(Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userService.getUserById(userDetails.getId());
    }

    @GetMapping("/latest-stories")
    public List<UserLatestStoryDTO> getUsersWithLatestStory() {
        return userService.getUsersWithLatestStory();
    }

}
