package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.User;
import com.example.social_media_app.service.UserService;

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
    @PutMapping("/{id}")  
    public User updateUser(@PathVariable UUID id, @RequestBody @Valid User user) throws Exception {
        return userService.updateUser(id, user);
    }
    @PutMapping("/follow/{id}/{userId}")  
    public User followUser(@PathVariable UUID id,@PathVariable UUID userId) throws Exception {
        return userService.followUser(id, userId);
    }
    @PostMapping()
    public User createUser(@RequestBody @Valid User user) {
        return userService.register(user);
    }



}
