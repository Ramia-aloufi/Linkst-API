package com.example.social_media_app.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @UuidGenerator
    private UUID id;
    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Comment must be associated with a user")
    @JsonIgnoreProperties({"password", "email", "roles", "posts", "comments", "createdAt", "updatedAt", "followers", "following","gender"})
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    @NotNull(message = "Comment must be associated with a post")
    private Post post;
    @ManyToMany
    @JoinTable(name = "comment_likes", joinColumns = @JoinColumn(name = "comment_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))

    private List<User> likes = new ArrayList<>();
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
