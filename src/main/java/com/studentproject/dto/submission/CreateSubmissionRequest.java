package com.studentproject.dto.submission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubmissionRequest {

    @NotNull
    private Long projectId;

    @NotBlank
    private String fileUrl;

    private String remarks;
}
