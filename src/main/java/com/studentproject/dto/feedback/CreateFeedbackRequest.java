package com.studentproject.dto.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFeedbackRequest {

    @NotNull
    private Long projectId;

    @NotNull
    private Long studentId;

    @NotBlank
    private String comments;

    private String grade;
}
