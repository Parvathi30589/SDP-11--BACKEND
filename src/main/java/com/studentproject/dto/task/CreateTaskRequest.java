package com.studentproject.dto.task;

import com.studentproject.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long assignedToId;

    @NotNull
    private Long projectId;

    private Long groupId;

    @NotNull
    private TaskStatus status;

    @NotNull
    private LocalDate deadline;
}
