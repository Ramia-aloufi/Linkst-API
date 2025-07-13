package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_media_app.exception.UserException;
import com.example.social_media_app.model.MessageReq;
import com.example.social_media_app.model.entity.Message;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.interfaces.MessageService;
import com.example.social_media_app.service.interfaces.UserService;

@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @PostMapping("/chat/{chatId}")
    public Message createMessage(@RequestBody MessageReq msg,@PathVariable UUID chatId,Authentication auth) throws UserException{
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        User us = userService.getUserById(user.getId());
        Message message = messageService.createMessage(us, chatId, msg);
        return message;
    }
    @GetMapping("/chat/{chatId}")
    public List<Message> findChatMessages(@PathVariable UUID chatId) throws UserException{        
        return messageService.geChatMessages(chatId);
    }


    
}
