package com.studentproject.dto.report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentReportResponse {
    private Long studentId;
    private String studentName;
    private long assignedTasks;
    private long completedTasks;
    private long submissions;
    private long feedbackCount;
}
