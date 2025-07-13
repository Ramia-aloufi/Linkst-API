package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.MessageReq;
import com.example.social_media_app.model.entity.Message;
import com.example.social_media_app.model.entity.User;

public interface MessageService {
    public Message createMessage(User user, UUID chatId, MessageReq req);
    public List<Message> geChatMessages(UUID chatId);
    
}
