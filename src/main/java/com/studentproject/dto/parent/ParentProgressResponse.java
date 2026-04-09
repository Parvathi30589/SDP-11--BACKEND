package com.studentproject.dto.parent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentProgressResponse {
    private Long studentId;
    private String studentName;
    private long totalTasks;
    private long completedTasks;
    private long totalSubmissions;
    private long totalFeedbacks;
}
