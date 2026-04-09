package com.studentproject.repository;

import com.studentproject.entity.Project;
import com.studentproject.entity.Task;
import com.studentproject.entity.User;
import com.studentproject.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
    List<Task> findByAssignedTo(User assignedTo);
    long countByProject(Project project);
    long countByProjectAndStatus(Project project, TaskStatus status);
    long countByAssignedTo(User assignedTo);
    long countByAssignedToAndStatus(User assignedTo, TaskStatus status);
}
