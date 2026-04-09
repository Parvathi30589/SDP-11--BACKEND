package com.studentproject.dto.milestone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMilestoneRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long projectId;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Boolean completed;
}
