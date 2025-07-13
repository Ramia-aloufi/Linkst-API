package com.example.social_media_app.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @UuidGenerator
    private UUID id;
    private String content ;
    private String image;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Chat chat ;
    private LocalDateTime timestamp;

    
    
}
