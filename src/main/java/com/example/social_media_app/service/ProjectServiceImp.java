package com.example.social_media_app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_media_app.model.entity.Project;
import com.example.social_media_app.model.entity.Tool;
import com.example.social_media_app.model.entity.User;
import com.example.social_media_app.model.request.ProjectRequest;
import com.example.social_media_app.repository.ProjectRepository;
import com.example.social_media_app.repository.ToolRepository;
import com.example.social_media_app.repository.UserRepository;
import com.example.social_media_app.service.interfaces.ProjectService;

import jakarta.transaction.Transactional;
@Service
public class ProjectServiceImp implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Project createProject(ProjectRequest project, UUID userId) {
        // Get the user who is creating the project
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Create a new project
        Project newProject = new Project();
        newProject.setTitle(project.name());
        newProject.setProjectImagesUrl(project.imageUrls());
        newProject.setDescription(project.description());
        newProject.setProjectUrl(project.projectUrl());
        newProject.setOwner(user);

        List<String> tools = project.tools();
        // Check if tools are not null and not empty and if each tool already exists
        if (tools != null && !tools.isEmpty()) {
            for (String tool : tools) {
                Tool existingTool = toolRepository.findByName(tool.toLowerCase().trim());
                if (existingTool != null) {
                    newProject.getTools().add(existingTool);
                } else {
                    Tool newTool = new Tool();
                    newTool.setName(tool.toLowerCase().trim());
                    Tool savedTool = toolRepository.save(newTool);
                    newProject.getTools().add(savedTool);
                }
            }
        }
        return projectRepository.save(newProject);
    }

    @Override
    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public List<Project> getUserProjects(UUID userId) {
        return projectRepository.findByOwnerId(userId);
    }

    @Override
    public Project updateProject(UUID projectId, ProjectRequest project) {
        Project existingProject = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        existingProject.setTitle(project.name());
        existingProject.setDescription(project.description());
        existingProject.getTools().clear();
        List<String> tools = project.tools();
        if (tools != null && !tools.isEmpty()) {
            for (String tool : tools) {
                Tool existingTool = toolRepository.findByName(tool.toLowerCase().trim());
                if (existingTool != null) {
                    existingProject.getTools().add(existingTool);
                } else {
                    Tool newTool = new Tool();
                    newTool.setName(tool.toLowerCase().trim());
                    Tool savedTool = toolRepository.save(newTool);
                    existingProject.getTools().add(savedTool);
                }
            }
        }
        return projectRepository.save(existingProject);
    }

    @Override
    public Project deleteProject(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(projectId);
        return project;
    }
    
}
