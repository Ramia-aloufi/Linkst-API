package com.example.social_media_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.social_media_app.model.entity.Chat;
import com.example.social_media_app.model.entity.User;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    public List<Chat> findByUsersId(UUID userId);
    @Query("select c from Chat c Where :user Member of c.users And :reqUser Member of c.users")
    public Chat findChatByUsersId(@Param("userId") User userId,@Param("reqUserId") User reqUserId);

}
