package com.studentproject.service.impl;

import com.studentproject.dto.project.CreateProjectRequest;
import com.studentproject.dto.project.ProjectResponse;
import com.studentproject.dto.project.UpdateProjectRequest;
import com.studentproject.entity.GroupMember;
import com.studentproject.entity.Project;
import com.studentproject.entity.Task;
import com.studentproject.entity.User;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.GroupMemberRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.TaskRepository;
import com.studentproject.service.ProjectService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {
        User teacher = authContextUtil.getCurrentUser();
        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(request.getStatus())
                .createdBy(teacher)
                .build();
        return map(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return map(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, UpdateProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setDeadline(request.getDeadline());
        project.setStatus(request.getStatus());

        return map(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        projectRepository.delete(project);
    }

    @Override
    public List<ProjectResponse> getMyProjects() {
        User student = authContextUtil.getCurrentUser();
        Set<Project> projects = new HashSet<>();

        List<Task> tasks = taskRepository.findByAssignedTo(student);
        tasks.forEach(task -> projects.add(task.getProject()));

        List<GroupMember> memberships = groupMemberRepository.findByStudent(student);
        memberships.forEach(member -> projects.add(member.getGroup().getProject()));

        return projects.stream().map(this::map).toList();
    }

    private ProjectResponse map(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .deadline(project.getDeadline())
                .createdById(project.getCreatedBy().getId())
                .createdByName(project.getCreatedBy().getName())
                .createdAt(project.getCreatedAt())
                .status(project.getStatus())
                .build();
    }
}
