package com.studentproject.service.impl;

import com.studentproject.dto.group.AddGroupMemberRequest;
import com.studentproject.dto.group.CreateGroupRequest;
import com.studentproject.dto.group.GroupResponse;
import com.studentproject.entity.Group;
import com.studentproject.entity.GroupMember;
import com.studentproject.entity.Project;
import com.studentproject.entity.User;
import com.studentproject.entity.enums.Role;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.exception.ValidationException;
import com.studentproject.repository.GroupMemberRepository;
import com.studentproject.repository.GroupRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public GroupResponse createGroup(CreateGroupRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Group group = Group.builder()
                .name(request.getName())
                .project(project)
                .build();

        return map(groupRepository.save(group));
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return map(group);
    }

    @Override
    public GroupResponse addMember(Long groupId, AddGroupMemberRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new ValidationException("Only users with STUDENT role can be added to groups");
        }
        if (groupMemberRepository.existsByGroupAndStudent(group, student)) {
            throw new ValidationException("Student already in group");
        }

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .student(student)
                .build();
        groupMemberRepository.save(groupMember);

        return map(group);
    }

    @Override
    public void removeMember(Long groupId, Long studentId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        GroupMember groupMember = groupMemberRepository.findByGroupAndStudent(group, student)
                .orElseThrow(() -> new ResourceNotFoundException("Group member not found"));

        groupMemberRepository.delete(groupMember);
    }

    private GroupResponse map(Group group) {
        List<Long> memberIds = groupMemberRepository.findByGroup(group)
                .stream()
                .map(gm -> gm.getStudent().getId())
                .toList();

        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .projectId(group.getProject().getId())
                .projectTitle(group.getProject().getTitle())
                .createdAt(group.getCreatedAt())
                .memberIds(memberIds)
                .build();
    }
}
