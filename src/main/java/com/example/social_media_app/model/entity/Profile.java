package com.example.social_media_app.model.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @UuidGenerator
    private UUID id;
    private String bio;
    private String location;
    private String website;
    private String profilePictureUrl;
    private String headerImageUrl;
    @OneToOne(mappedBy = "profile")
    @JsonBackReference
private User user;
}
