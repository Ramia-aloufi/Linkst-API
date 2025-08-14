package com.example.social_media_app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.Profile;
import com.example.social_media_app.repository.ProfileRepository;
import com.example.social_media_app.service.interfaces.ProfileService;

@Service
public class ProfileServiceImp implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile getProfileByUserId(UUID userId) throws Exception {
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new Exception("Profile not found");
        }
        return profile;
    }

    @Override
    public Profile updateProfile(Profile profile) throws Exception {
        Profile existingProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new Exception("Profile not found"));

        if (profile.getBio() != null)
            existingProfile.setBio(profile.getBio());
        if (profile.getLocation() != null)
            existingProfile.setLocation(profile.getLocation());
        if (profile.getWebsite() != null)
            existingProfile.setWebsite(profile.getWebsite());
        if (profile.getProfilePictureUrl() != null)
            existingProfile.setProfilePictureUrl(profile.getProfilePictureUrl());
        if (profile.getHeaderImageUrl() != null)
            existingProfile.setHeaderImageUrl(profile.getHeaderImageUrl());
        return profileRepository.save(existingProfile);
    }

    @Override
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

}
