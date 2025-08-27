package com.example.social_media_app.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "posts")
public class Post {
    @Id
    @UuidGenerator
    private UUID id;
    @NotBlank(message = "Caption cannot be blank")
    @Column(nullable = true)
    @Size(max = 255, message = "Caption must not exceed 255 characters")
    private String caption;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Post must be associated with a user")
    @JsonBackReference
    @JsonIgnoreProperties({"email","password", "createdAt", "updatedAt","followers", "following","gender"})
    private User user;
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    @NotBlank(message = "Content cannot be blank")
    @Column(nullable = false)
    private String content;
    @Column(nullable = true)
    private String media;
    @Column(nullable = true)
    private String type;
    @ManyToMany
    @JoinTable(
    name = "posts_likes",
    joinColumns = @JoinColumn(name = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "likes_id"),
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "likes_id"})
    }
)
    @JsonIgnoreProperties({"firstName", "lastName", "email", "username", "password", "createdAt", "updatedAt","followers", "following","gender","savedPosts","profile","stories","posts"})
    private List<User> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties({"post", "likes", "createdAt", "updatedAt"})
    private List<Comment> comments = new ArrayList<>();
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
       @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setImageUrl(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImageUrl'");
    }

}
