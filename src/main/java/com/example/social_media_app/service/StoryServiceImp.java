package com.example.social_media_app.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.social_media_app.mapper.StoryMapper;
import com.example.social_media_app.mapper.UserMapper;
import com.example.social_media_app.model.dto.StoryDto;
import com.example.social_media_app.model.dto.UserStoriesDto;
import com.example.social_media_app.model.entity.Story;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.repository.StoryRepository;
import com.example.social_media_app.service.interfaces.StoryService;
import com.example.social_media_app.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImp implements StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;
    private final StoryMapper storyMapper;
    private final UserMapper userMapper;

    public Story createStory(Story story, UUID userId) throws Exception {
        User user = userService.getUserById(userId);
        Story newStory = new Story();
        newStory.setCaption(story.getCaption());
        newStory.setMedia(story.getMedia());
        newStory.setMediaType(story.getMediaType());
        newStory.setUser(user);
        storyRepository.save(newStory);
        return newStory;

    }

    public List<UserStoriesDto> getLatestStories() throws Exception {
        List<Story> stories = storyRepository.findLatestStories();
        Map<User, List<Story>> grouped = stories.stream()
                .collect(Collectors.groupingBy(Story::getUser));
        return grouped.entrySet().stream().map(entry -> {
            UserStoriesDto dto = new UserStoriesDto();
            dto.setUser(userMapper.toDto(entry.getKey()));
            dto.setStories(entry.getValue().stream()
                    .map(storyMapper::toStoryDto)
                    .toArray(StoryDto[]::new));
            return dto;
        }).toList();
    }

}
