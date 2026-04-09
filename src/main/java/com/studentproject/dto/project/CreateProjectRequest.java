package com.studentproject.dto.project;

import com.studentproject.entity.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateProjectRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate deadline;

    @NotNull
    private ProjectStatus status;
}
