package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.Chat;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.ChatRepository;
import com.example.social_media_app.service.interfaces.ChatService;

@Service
public class ChatServiceImp implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(User reqUser, User user2) throws Exception {
        Chat chatExist = chatRepository.findChatByUsersId(reqUser, user2);
        if (chatExist != null) {
            return chatExist;
        }
        Chat newChat = new Chat();
        newChat.getUsers().add(reqUser);
        newChat.getUsers().add(user2);
        chatRepository.save(newChat);
        return newChat;

    }

    public Chat getChatById(UUID chatId) throws Exception {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new Exception("Chat not found"));
        return chat;
    }

    public List<Chat> getAllChatsByUserId(UUID userId) throws Exception {
        List<Chat> userChat = chatRepository.findAllByUserId(userId);
        if (userChat == null || userChat.isEmpty()) {
            throw new Exception("No chats found for this user");
        }
                // remove the current user from chat.users
        for (Chat chat : userChat) {
            chat.setUsers(
                chat.getUsers()
                    .stream()
                    .filter(u -> !u.getId().equals(userId))
                    .toList()
            );
        }
        return userChat;
    }

}
