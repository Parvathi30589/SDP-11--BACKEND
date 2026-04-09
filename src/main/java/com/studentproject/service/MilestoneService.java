package com.studentproject.service;

import com.studentproject.dto.milestone.CreateMilestoneRequest;
import com.studentproject.dto.milestone.MilestoneResponse;
import com.studentproject.dto.milestone.UpdateMilestoneRequest;

import java.util.List;

public interface MilestoneService {
    MilestoneResponse createMilestone(CreateMilestoneRequest request);
    List<MilestoneResponse> getMilestonesByProject(Long projectId);
    MilestoneResponse updateMilestone(Long id, UpdateMilestoneRequest request);
    void deleteMilestone(Long id);
}
