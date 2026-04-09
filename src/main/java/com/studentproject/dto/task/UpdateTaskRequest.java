package com.studentproject.dto.task;

import com.studentproject.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long assignedToId;

    private Long groupId;

    @NotNull
    private TaskStatus status;

    @NotNull
    private LocalDate deadline;
}
