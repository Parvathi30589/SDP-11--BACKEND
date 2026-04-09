package com.studentproject.controller;

import com.studentproject.dto.parent.LinkParentStudentRequest;
import com.studentproject.dto.parent.ParentProgressResponse;
import com.studentproject.dto.parent.ParentStudentResponse;
import com.studentproject.service.ParentService;
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
@RequestMapping("/api/parent")
@RequiredArgsConstructor
@Tag(name = "Parent", description = "Parent APIs")
@SecurityRequirement(name = "bearerAuth")
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/link")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Link parent to student", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Link created")
    public ResponseEntity<Void> linkParentStudent(@Valid @RequestBody LinkParentStudentRequest request) {
        parentService.linkParentToStudent(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-students")
    @PreAuthorize("hasRole('PARENT')")
    @Operation(summary = "Get linked students", description = "Parent only")
    @ApiResponse(responseCode = "200", description = "Students fetched")
    public ResponseEntity<List<ParentStudentResponse>> getMyStudents() {
        return ResponseEntity.ok(parentService.getMyStudents());
    }

    @GetMapping("/progress/{studentId}")
    @PreAuthorize("hasRole('PARENT')")
    @Operation(summary = "Get student progress", description = "Parent only")
    @ApiResponse(responseCode = "200", description = "Progress fetched")
    public ResponseEntity<ParentProgressResponse> getStudentProgress(@PathVariable Long studentId) {
        return ResponseEntity.ok(parentService.getStudentProgress(studentId));
    }
}
