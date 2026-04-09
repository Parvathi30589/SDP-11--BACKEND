package com.studentproject.dto.milestone;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MilestoneResponse {
    private Long id;
    private String title;
    private String description;
    private Long projectId;
    private LocalDate dueDate;
    private Boolean completed;
}
