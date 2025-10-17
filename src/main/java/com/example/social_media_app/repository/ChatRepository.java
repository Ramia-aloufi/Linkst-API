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

    @Query("select c from Chat c Where :userId Member of c.users And :reqUserId Member of c.users")
    public Chat findChatByUsersId(@Param("userId") User userId, @Param("reqUserId") User reqUserId);

@Query("""
    SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId
""")
    List<Chat> findAllChatsByUserId(@Param("userId") UUID userId);

    @Query("SELECT DISTINCT u FROM Chat c JOIN c.users u " +
            "WHERE c.id IN (SELECT c2.id FROM Chat c2 JOIN c2.users u2 WHERE u2.id = :userId) " +
            "AND u.id <> :userId")
    List<Chat> findChatPartners(@Param("userId") UUID userId);

}
