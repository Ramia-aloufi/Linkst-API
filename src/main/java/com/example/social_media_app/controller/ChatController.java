package com.example.social_media_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.model.entity.Chat;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.ChatRequest;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.interfaces.ChatService;
import com.example.social_media_app.service.interfaces.UserService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public Chat createChat(@RequestBody ChatRequest chatRequest, Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User reqUser = userService.getUserById(userDetails.getId());

        User user2 = userService.getUserById(chatRequest.getUserId());

        return chatService.createChat(reqUser, user2);

    }

    @GetMapping("/all")
    public List<Chat> getAllChatsByUserId(Authentication auth) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return chatService.getAllChatsByUserId(userDetails.getId());
    }

}
