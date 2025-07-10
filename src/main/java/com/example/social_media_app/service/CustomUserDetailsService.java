package com.example.social_media_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email: \n" + email);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        System.out.println("user: \n" + user.getFirstName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        CustomUserDetails userDetails = new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(),
                authorities);
        System.out.println("userDetails: \n" + userDetails.getId());
        return userDetails;
    }

}
