package com.studentproject.dto.submission;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionResponse {
    private Long id;
    private Long projectId;
    private String projectTitle;
    private Long submittedById;
    private String submittedByName;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private String remarks;
}
