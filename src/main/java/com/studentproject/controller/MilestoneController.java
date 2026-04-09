package com.studentproject.controller;

import com.studentproject.dto.milestone.CreateMilestoneRequest;
import com.studentproject.dto.milestone.MilestoneResponse;
import com.studentproject.dto.milestone.UpdateMilestoneRequest;
import com.studentproject.service.MilestoneService;
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
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
@Tag(name = "Milestones", description = "Milestone APIs")
@SecurityRequirement(name = "bearerAuth")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Create milestone", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Milestone created")
    public ResponseEntity<MilestoneResponse> createMilestone(@Valid @RequestBody CreateMilestoneRequest request) {
        return ResponseEntity.ok(milestoneService.createMilestone(request));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get milestones by project")
    @ApiResponse(responseCode = "200", description = "Milestones fetched")
    public ResponseEntity<List<MilestoneResponse>> getMilestonesByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(milestoneService.getMilestonesByProject(projectId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Update milestone", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Milestone updated")
    public ResponseEntity<MilestoneResponse> updateMilestone(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateMilestoneRequest request) {
        return ResponseEntity.ok(milestoneService.updateMilestone(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Delete milestone", description = "Teacher only")
    @ApiResponse(responseCode = "204", description = "Milestone deleted")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.noContent().build();
    }
}
