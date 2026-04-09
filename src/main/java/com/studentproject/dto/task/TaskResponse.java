package com.studentproject.dto.task;

import com.studentproject.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Long assignedToId;
    private String assignedToName;
    private Long projectId;
    private Long groupId;
    private TaskStatus status;
    private LocalDate deadline;
    private LocalDateTime createdAt;
}
