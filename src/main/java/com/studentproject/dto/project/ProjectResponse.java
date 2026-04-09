package com.studentproject.dto.project;

import com.studentproject.entity.enums.ProjectStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private ProjectStatus status;
}
