package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.MessageReq;
import com.example.social_media_app.model.dto.MessageDTO;
import com.example.social_media_app.model.entity.User;

public interface MessageService {
    public MessageDTO createMessage(User user, UUID chatId, MessageReq req);
    public List<MessageDTO> geChatMessages(UUID chatId);
    
}
