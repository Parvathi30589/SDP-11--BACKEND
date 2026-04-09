package com.studentproject.dto.report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectReportResponse {
    private Long projectId;
    private String projectTitle;
    private long totalGroups;
    private long totalTasks;
    private long completedTasks;
    private long totalMilestones;
    private long completedMilestones;
    private long totalSubmissions;
}
