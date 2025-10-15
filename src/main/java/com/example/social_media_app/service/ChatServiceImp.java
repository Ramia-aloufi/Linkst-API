package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.ChatDTO;
import com.example.social_media_app.model.ChatUserDTO;
import com.example.social_media_app.model.ProfileIMG;
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

    public List<ChatDTO> getAllChatsByUserId(UUID userId) throws Exception {
        List<Chat> userChat = chatRepository.findAllChatsByUserId(userId);
        if (userChat == null || userChat.isEmpty()) {
            throw new Exception("No chats found for this user");
        }

        return userChat.stream().map(chat -> {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(chat.getId());
            chatDTO.setLastMessage(chat.getMessages().getFirst().getContent());
            chatDTO.setUserId(userId);
            chatDTO.setFullName(chat.getUsers().stream().filter(u -> !u.getId().equals(userId)).findFirst().get().getFullName());
            chatDTO.setProfileImage(chat.getUsers().stream().filter(u -> !u.getId().equals(userId)).findFirst().get().getProfile() != null ?
                    chat.getUsers().stream().filter(u -> !u.getId().equals(userId)).findFirst().get().getProfile().getProfilePictureUrl() :
                    "https://res.cloudinary.com/dhrj6nlxm/image/upload/v1760296929/default-avatar-profile-icon-social-600nw-1906669723.jpg_zwbrok.webp");
            return chatDTO;
        }).collect(Collectors.toList());
    }
}
