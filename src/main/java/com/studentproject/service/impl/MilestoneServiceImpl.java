package com.studentproject.service.impl;

import com.studentproject.dto.milestone.CreateMilestoneRequest;
import com.studentproject.dto.milestone.MilestoneResponse;
import com.studentproject.dto.milestone.UpdateMilestoneRequest;
import com.studentproject.entity.Milestone;
import com.studentproject.entity.Project;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.MilestoneRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    @Override
    public MilestoneResponse createMilestone(CreateMilestoneRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Milestone milestone = Milestone.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .project(project)
                .dueDate(request.getDueDate())
                .completed(request.getCompleted())
                .build();

        return map(milestoneRepository.save(milestone));
    }

    @Override
    public List<MilestoneResponse> getMilestonesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return milestoneRepository.findByProject(project).stream().map(this::map).toList();
    }

    @Override
    public MilestoneResponse updateMilestone(Long id, UpdateMilestoneRequest request) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));

        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setDueDate(request.getDueDate());
        milestone.setCompleted(request.getCompleted());

        return map(milestoneRepository.save(milestone));
    }

    @Override
    public void deleteMilestone(Long id) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));
        milestoneRepository.delete(milestone);
    }

    private MilestoneResponse map(Milestone milestone) {
        return MilestoneResponse.builder()
                .id(milestone.getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .projectId(milestone.getProject().getId())
                .dueDate(milestone.getDueDate())
                .completed(milestone.getCompleted())
                .build();
    }
}
