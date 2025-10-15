package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.social_media_app.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message,UUID> {
    public List<Message> findByChatId(UUID chatId);
        
}
