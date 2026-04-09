package com.studentproject.controller;

import com.studentproject.dto.report.ProjectReportResponse;
import com.studentproject.dto.report.StudentReportResponse;
import com.studentproject.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Teacher reports APIs")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('TEACHER')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get project summary report", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Report fetched")
    public ResponseEntity<ProjectReportResponse> getProjectReport(@PathVariable Long projectId) {
        return ResponseEntity.ok(reportService.getProjectSummary(projectId));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get student performance report", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Report fetched")
    public ResponseEntity<StudentReportResponse> getStudentReport(@PathVariable Long studentId) {
        return ResponseEntity.ok(reportService.getStudentPerformance(studentId));
    }
}
