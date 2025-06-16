package com.example.social_media_app.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "chat_image", nullable = true)
    private String chatImage;
    @Column(name = "chat_name", nullable = true)
    private String chatName;
    @ManyToMany
    private List<User> users = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();

}
