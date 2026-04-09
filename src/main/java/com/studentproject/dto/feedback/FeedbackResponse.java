package com.studentproject.dto.feedback;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedbackResponse {
    private Long id;
    private Long projectId;
    private String projectTitle;
    private Long studentId;
    private String studentName;
    private Long givenById;
    private String givenByName;
    private String comments;
    private String grade;
    private LocalDateTime createdAt;
}
