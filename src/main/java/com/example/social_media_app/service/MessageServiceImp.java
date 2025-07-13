package com.example.social_media_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.MessageReq;
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
    public Message createMessage(User user, UUID chatId, MessageReq req) {

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
        

        return savedMessage;
        
        }

    @Override
    public List<Message> geChatMessages(UUID chatId) {
     return messageRepository.findByChatId(chatId);

    }
    
}
