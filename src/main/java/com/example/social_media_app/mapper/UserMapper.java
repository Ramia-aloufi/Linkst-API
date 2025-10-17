package com.example.social_media_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.social_media_app.model.dto.UserDto;
import com.example.social_media_app.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.profile.profilePictureUrl", target = "profileImage")
    UserDto toDto(User user);
}
