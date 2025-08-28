package com.example.social_media_app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_media_app.model.entity.Project;
import com.example.social_media_app.model.request.ProjectRequest;
import com.example.social_media_app.model.response.CustomUserDetails;
import com.example.social_media_app.service.CloudinaryService;
import com.example.social_media_app.service.interfaces.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping
    public Project createProject(Authentication auth,@RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("projectUrl") String projectUrl,
                                  @RequestParam("tools") List<String> tools,
                                  @RequestParam("images") MultipartFile[] images) throws IOException {
     CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
     UUID userId = userDetails.getId();


        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : images) {
            var uploadResult = cloudinaryService.uploadFile(file, "projects");
            String videoUrl = uploadResult.get("url").toString();
            imageUrls.add(videoUrl);
        }

        ProjectRequest projectRequest = new ProjectRequest(title, description, tools, imageUrls, projectUrl);
        return projectService.createProject(projectRequest, userId);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable UUID id) {
        return projectService.getProjectById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Project> getUserProjects(@PathVariable UUID userId) {
        return projectService.getUserProjects(userId);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable UUID id, @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(id, projectRequest);
    }

    @DeleteMapping("/{id}")
    public Project deleteProject(@PathVariable UUID id) {
        return projectService.deleteProject(id);
    }
}
