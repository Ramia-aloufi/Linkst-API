package com.example.social_media_app.model.request;

import java.util.List;

public record ProjectRequest(String name, String description, List<String> tools, List<String> imageUrls, String projectUrl) {
}
