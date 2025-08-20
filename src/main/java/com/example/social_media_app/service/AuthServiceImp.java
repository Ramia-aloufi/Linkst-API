package com.example.social_media_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.social_media_app.config.JwtProvider;
import com.example.social_media_app.model.entity.Profile;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.model.response.LoginRequest;
import com.example.social_media_app.repository.UserRepository;
import com.example.social_media_app.service.interfaces.AuthService;
import com.example.social_media_app.service.interfaces.ProfileService;
import com.example.social_media_app.service.interfaces.UserService;

@Service
public class AuthServiceImp implements AuthService {

        @Autowired
        UserService userService;
        @Autowired
        UserRepository userRepository;
        @Autowired
        PasswordEncoder passwordEncoder;
        @Autowired
        CustomUserDetailsService customUserDetailsService;
        @Autowired
        ProfileService profileService;

        public String signUp(User user)  {
                // Check if the user already exists
                if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
                        throw new RuntimeException("User already exists");
                }
                // Create empty/default profile
                Profile profile = new Profile();
                profile.setBio("");
                profile.setLocation("");
                profile.setWebsite("");
                profile.setProfilePictureUrl(null);
                profile.setHeaderImageUrl(null);


                // Create a new user
                User newUser = new User();
                newUser.setEmail(user.getEmail());
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setGender(user.getGender());

                profile.setUser(newUser);
                newUser.setProfile(profile);

                // Save the user to the database
                User savedUser = userRepository.save(newUser);
                profileService.createProfile(profile);

                
                CustomUserDetails userDetails = new CustomUserDetails(savedUser.getId(), savedUser.getEmail(),
                                savedUser.getPassword(), null);

                // Create an authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                savedUser.getPassword());

                // Generate a JWT token for the user
                String token = JwtProvider.generateToken(authentication);
                return token;
        }

        public String login(LoginRequest loginRequest) {
                // Authenticate the user
                CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

                // Validate password
                if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                        throw new BadCredentialsException("Invalid credentials");
                }

                // Set authentication context
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());

                // Generate token
                String token = JwtProvider.generateToken(authentication);

                return token;

        }
}
