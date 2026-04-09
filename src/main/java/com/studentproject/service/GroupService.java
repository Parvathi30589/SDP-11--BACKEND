package com.studentproject.service;

import com.studentproject.dto.group.AddGroupMemberRequest;
import com.studentproject.dto.group.CreateGroupRequest;
import com.studentproject.dto.group.GroupResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(CreateGroupRequest request);
    List<GroupResponse> getAllGroups();
    GroupResponse getGroupById(Long id);
    GroupResponse addMember(Long groupId, AddGroupMemberRequest request);
    void removeMember(Long groupId, Long studentId);
}
