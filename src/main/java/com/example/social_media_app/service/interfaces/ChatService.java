package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Chat;
import com.example.social_media_app.model.entity.User;

public interface ChatService {

    Chat createChat(User reqUser, User user2)throws Exception;
    Chat getChatById(UUID chatId)throws Exception;
    List<Chat> getAllChatsByUserId(UUID userId)throws Exception;

}
