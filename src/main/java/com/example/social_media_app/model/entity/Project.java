package com.example.social_media_app.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
public class Project {
    @Id
    @UuidGenerator
    UUID id;
    @NotBlank(message = "Project title is required")
    @Size(min = 2, max = 100, message = "Project title must be between 2 and 100 characters")
    String title;
    @NotBlank(message = "Project description is required")
    @Size(min = 10, max = 500, message = "Project description must be between 10 and 500 characters")
    String description;
    @ManyToOne
    @JsonIgnoreProperties({"reels", "password","projects", "email", "createdAt","followers","following","comments","likes", "stories","posts","comments","gender","savedPosts"})
    User owner;
    @NotBlank(message = "Project images URL is required")
    List<String> projectImagesUrl = new ArrayList<>();
    @ManyToMany
    List<Tool> tools = new ArrayList<>();
    String projectUrl;
    LocalDate createdAt = LocalDate.now();
}
