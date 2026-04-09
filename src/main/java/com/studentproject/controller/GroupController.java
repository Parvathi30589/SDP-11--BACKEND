package com.studentproject.controller;

import com.studentproject.dto.group.AddGroupMemberRequest;
import com.studentproject.dto.group.CreateGroupRequest;
import com.studentproject.dto.group.GroupResponse;
import com.studentproject.service.GroupService;
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
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "Group APIs")
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Create group", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Group created")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @GetMapping
    @Operation(summary = "Get all groups")
    @ApiResponse(responseCode = "200", description = "Groups fetched")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    @ApiResponse(responseCode = "200", description = "Group fetched")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Add student to group", description = "Teacher only")
    @ApiResponse(responseCode = "200", description = "Member added")
    public ResponseEntity<GroupResponse> addMember(@PathVariable Long id,
                                                   @Valid @RequestBody AddGroupMemberRequest request) {
        return ResponseEntity.ok(groupService.addMember(id, request));
    }

    @DeleteMapping("/{id}/members/{studentId}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Remove student from group", description = "Teacher only")
    @ApiResponse(responseCode = "204", description = "Member removed")
    public ResponseEntity<Void> removeMember(@PathVariable Long id, @PathVariable Long studentId) {
        groupService.removeMember(id, studentId);
        return ResponseEntity.noContent().build();
    }
}
