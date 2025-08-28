package com.example.social_media_app.model.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Tool {
    @UuidGenerator
    @Id
    private String id;
    @NotBlank(message = "Tool name is Required")
    @Size(min = 2, max = 50, message = "Tool name must be between 2 and 50 characters")
    private String name;

}
