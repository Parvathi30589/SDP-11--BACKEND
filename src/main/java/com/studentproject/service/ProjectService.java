package com.studentproject.service;

import com.studentproject.dto.project.CreateProjectRequest;
import com.studentproject.dto.project.ProjectResponse;
import com.studentproject.dto.project.UpdateProjectRequest;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(CreateProjectRequest request);
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(Long id);
    ProjectResponse updateProject(Long id, UpdateProjectRequest request);
    void deleteProject(Long id);
    List<ProjectResponse> getMyProjects();
}
