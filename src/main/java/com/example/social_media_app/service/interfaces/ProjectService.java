package com.example.social_media_app.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.social_media_app.model.entity.Project;
import com.example.social_media_app.model.request.ProjectRequest;

public interface ProjectService {
    Project createProject(ProjectRequest project, UUID userId);
    Project getProjectById(UUID projectId);
    List<Project> getUserProjects(UUID userId);
    Project updateProject(UUID projectId, ProjectRequest project);
    Project deleteProject(UUID projectId);
}
