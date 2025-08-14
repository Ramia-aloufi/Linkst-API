package com.example.social_media_app.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.model.entity.Profile;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/user")
    public ResponseEntity<Profile> getProfile(Authentication authentication) throws Exception {
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUserId(userDetail.getId());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable UUID id,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String website,
            @RequestPart(required = false) MultipartFile profilePictureUrl,
            @RequestPart(required = false) MultipartFile headerImageUrl) throws Exception {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setBio(bio);
        profile.setLocation(location);
        profile.setWebsite(website);

        if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
            Map<String, String> uploadedUrl = cloudinaryService.uploadFile(profilePictureUrl, "profile/pictures");
            profile.setProfilePictureUrl(uploadedUrl.get("url"));
        }
        if (headerImageUrl != null && !headerImageUrl.isEmpty()) {
            Map<String, String> uploadedUrl = cloudinaryService.uploadFile(headerImageUrl, "profile/headers");
            profile.setHeaderImageUrl(uploadedUrl.get("url"));
        }

        Profile updatedProfile = profileService.updateProfile(profile);
        return ResponseEntity.ok(updatedProfile);
    }
}
