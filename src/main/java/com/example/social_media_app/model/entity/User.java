package com.example.social_media_app.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(nullable = false, name = "first_name")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Column(nullable = false, name = "password")
    @Size(min = 3, max = 20, message = "Password must be at least 3 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password must contain only alphanumeric characters")
    private String password;
    @Column(unique = true, nullable = false, name = "email")
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^(male|female)$", message ="gender required !")
    private String gender;
    private List<UUID> followers = new ArrayList<>() ;
    private List<UUID> following = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<Post> savedPosts = new ArrayList<>();


    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

}