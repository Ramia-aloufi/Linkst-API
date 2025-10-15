package com.example.social_media_app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.exception.UserException;
import com.example.social_media_app.model.MessageDTO;
import com.example.social_media_app.model.MessageReq;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.MessageService;
import com.example.social_media_app.service.interfaces.UserService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/chat/{chatId}")
    public MessageDTO createMessage(
        @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "content", required = false) String content,
             @PathVariable UUID chatId,
            Authentication auth)
            throws UserException, Exception {
                System.err.println("Received content: " + content);
    if ((content == null || content.trim().isEmpty()) && (image == null || image.isEmpty())) {
        throw new IllegalArgumentException("Message cannot be empty. Please provide text or an image.");
    }
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        User us = userService.getUserById(user.getId());
        MessageReq msg = new MessageReq();
        System.err.println("content: " + content);
        if (content != null && !content.isEmpty()) {
            msg.setContent(content);
        }
        if (image != null) {
            var uploadResult = cloudinaryService.uploadFile(image, "messages");
            msg.setImage(uploadResult.get("url"));
        }

        MessageDTO message = messageService.createMessage(us, chatId, msg);
        return message;
    }

    @GetMapping("/chat/{chatId}")
    public List<MessageDTO> findChatMessages(@PathVariable UUID chatId) throws UserException {
        return messageService.geChatMessages(chatId);
    }

}
