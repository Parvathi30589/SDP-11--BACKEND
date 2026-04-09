package com.studentproject.service;

import com.studentproject.dto.task.CreateTaskRequest;
import com.studentproject.dto.task.TaskResponse;
import com.studentproject.dto.task.UpdateTaskRequest;
import com.studentproject.dto.task.UpdateTaskStatusRequest;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);
    List<TaskResponse> getTasksByProject(Long projectId);
    List<TaskResponse> getMyTasks();
    TaskResponse updateTask(Long id, UpdateTaskRequest request);
    TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request);
    void deleteTask(Long id);
}
