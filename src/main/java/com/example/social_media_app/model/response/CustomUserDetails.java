package com.example.social_media_app.model.response;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private UUID id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

}
