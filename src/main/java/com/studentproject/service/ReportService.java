package com.studentproject.service;

import com.studentproject.dto.report.ProjectReportResponse;
import com.studentproject.dto.report.StudentReportResponse;

public interface ReportService {
    ProjectReportResponse getProjectSummary(Long projectId);
    StudentReportResponse getStudentPerformance(Long studentId);
}
