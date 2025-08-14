package com.example.social_media_app.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Story {
    @Id
    @UuidGenerator
    private UUID id;
    @NotBlank(message = "Caption is required")
    private String caption;
    @NotBlank(message = "Media is required")
    @Column(nullable = false, name = "media")
    private String media;
    @NotBlank(message = "Media type is required")
    @Column(nullable = false, name = "media_type")
    private String mediaType;
    @NotNull(message = "Story must be associated with a user")
    @ManyToOne
    private User user;
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
