package com.studentproject.controller;

import com.studentproject.dto.submission.CreateSubmissionRequest;
import com.studentproject.dto.submission.SubmissionResponse;
import com.studentproject.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Tag(name = "Submissions", description = "Submission APIs")
@SecurityRequirement(name = "bearerAuth")
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Submit project", description = "Student only")
    @ApiResponse(responseCode = "200", description = "Submission created")
    public ResponseEntity<SubmissionResponse> createSubmission(@Valid @RequestBody CreateSubmissionRequest request) {
        return ResponseEntity.ok(submissionService.createSubmission(request));
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "View submissions by project", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Submissions fetched")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByProject(projectId));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "View own submissions", description = "Student only")
    @ApiResponse(responseCode = "200", description = "Submissions fetched")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions() {
        return ResponseEntity.ok(submissionService.getMySubmissions());
    }
}
