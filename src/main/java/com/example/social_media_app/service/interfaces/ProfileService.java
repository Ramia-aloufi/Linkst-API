package com.example.social_media_app.service.interfaces;

import java.util.UUID;

import com.example.social_media_app.model.entity.Profile;

public interface ProfileService {
   Profile getProfileByUserId(UUID userId) throws Exception;
   Profile updateProfile(Profile profile) throws Exception;
   Profile createProfile(Profile profile) ;

}
