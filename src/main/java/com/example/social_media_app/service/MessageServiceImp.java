package com.example.social_media_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.MessageDTO;
import com.example.social_media_app.model.MessageReq;
import com.example.social_media_app.model.UserDTO;
import com.example.social_media_app.model.entity.Chat;
import com.example.social_media_app.model.entity.Message;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.ChatRepository;
import com.example.social_media_app.repository.MessageRepository;
import com.example.social_media_app.service.interfaces.MessageService;

@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ChatRepository chatRepository;

    @Override
    public MessageDTO createMessage(User user, UUID chatId, MessageReq req) {

        Chat chat = chatRepository.findById(chatId).orElseThrow();

        Message newMsg = new Message();
        newMsg.setChat(chat);
        newMsg.setUser(user);
        newMsg.setContent(req.getContent());
        newMsg.setImage(req.getImage());
        newMsg.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(newMsg);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(savedMessage.getId());
        messageDTO.setContent(savedMessage.getContent());
        messageDTO.setImage(savedMessage.getImage());
        messageDTO.setTimestamp(savedMessage.getTimestamp());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedMessage.getUser().getId());
        userDTO.setFullName(savedMessage.getUser().getFullName());
        userDTO.setProfileImage(savedMessage.getUser().getProfile() != null
                ? savedMessage.getUser().getProfile().getProfilePictureUrl()
                : "https://res.cloudinary.com/dhrj6nlxm/image/upload/v1760296929/default-avatar-profile-icon-social-600nw-1906669723.jpg_zwbrok.webp");
        messageDTO.setUser(userDTO);
        return messageDTO;
    }

    @Override
    public List<MessageDTO> geChatMessages(UUID chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("No messages found for this chat");
        }
        List<MessageDTO> messageDTOs = messages.stream().map(message -> {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setContent(message.getContent());
            messageDTO.setImage(message.getImage());
            messageDTO.setTimestamp(message.getTimestamp());
            UserDTO userDTO = new UserDTO();
            userDTO.setId(message.getUser().getId());
            userDTO.setFullName(message.getUser().getFullName());
            userDTO.setProfileImage(message.getUser().getProfile() != null
                    ? message.getUser().getProfile().getProfilePictureUrl()
                    : "https://res.cloudinary.com/dhrj6nlxm/image/upload/v1760296929/default-avatar-profile-icon-social-600nw-1906669723.jpg_zwbrok.webp");
            messageDTO.setUser(userDTO);
            return messageDTO;
        }).collect(Collectors.toList());
        return messageDTOs;

    }

}
