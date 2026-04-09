package com.studentproject.service.impl;

import com.studentproject.dto.task.CreateTaskRequest;
import com.studentproject.dto.task.TaskResponse;
import com.studentproject.dto.task.UpdateTaskRequest;
import com.studentproject.dto.task.UpdateTaskStatusRequest;
import com.studentproject.entity.Group;
import com.studentproject.entity.Project;
import com.studentproject.entity.Task;
import com.studentproject.entity.User;
import com.studentproject.entity.enums.Role;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.exception.UnauthorizedException;
import com.studentproject.repository.GroupRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.TaskRepository;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.TaskService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        User student = userRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned student not found"));
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Group group = request.getGroupId() == null ? null : groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new UnauthorizedException("Task can only be assigned to a student");
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .assignedTo(student)
                .project(project)
                .group(group)
                .status(request.getStatus())
                .deadline(request.getDeadline())
                .build();

        return map(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getTasksByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return taskRepository.findByProject(project).stream().map(this::map).toList();
    }

    @Override
    public List<TaskResponse> getMyTasks() {
        User student = authContextUtil.getCurrentUser();
        return taskRepository.findByAssignedTo(student).stream().map(this::map).toList();
    }

    @Override
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        User student = userRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned student not found"));
        Group group = request.getGroupId() == null ? null : groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssignedTo(student);
        task.setGroup(group);
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());

        return map(taskRepository.save(task));
    }

    @Override
    public TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        User current = authContextUtil.getCurrentUser();
        if (!task.getAssignedTo().getId().equals(current.getId())) {
            throw new UnauthorizedException("You can only update your own task status");
        }

        task.setStatus(request.getStatus());
        return map(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    private TaskResponse map(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .assignedToId(task.getAssignedTo().getId())
                .assignedToName(task.getAssignedTo().getName())
                .projectId(task.getProject().getId())
                .groupId(task.getGroup() == null ? null : task.getGroup().getId())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
