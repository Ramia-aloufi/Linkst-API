package com.example.social_media_app.mapper;

import org.mapstruct.Mapper;

import com.example.social_media_app.model.dto.StoryDto;
import com.example.social_media_app.model.entity.Story;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface StoryMapper {
    StoryDto toStoryDto(Story stories);
}
