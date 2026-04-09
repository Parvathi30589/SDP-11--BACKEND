package com.studentproject.controller;

import com.studentproject.dto.feedback.CreateFeedbackRequest;
import com.studentproject.dto.feedback.FeedbackResponse;
import com.studentproject.service.FeedbackService;
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
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Feedback APIs")
@SecurityRequirement(name = "bearerAuth")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Give feedback", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Feedback created")
    public ResponseEntity<FeedbackResponse> createFeedback(@Valid @RequestBody CreateFeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.createFeedback(request));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get feedback by project")
    @ApiResponse(responseCode = "200", description = "Feedback fetched")
    public ResponseEntity<List<FeedbackResponse>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByProject(projectId));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get feedback by student")
    @ApiResponse(responseCode = "200", description = "Feedback fetched")
    public ResponseEntity<List<FeedbackResponse>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByStudent(studentId));
    }
}
