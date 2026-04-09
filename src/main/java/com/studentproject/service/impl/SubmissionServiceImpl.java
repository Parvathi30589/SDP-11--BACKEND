package com.studentproject.service.impl;

import com.studentproject.dto.submission.CreateSubmissionRequest;
import com.studentproject.dto.submission.SubmissionResponse;
import com.studentproject.entity.Project;
import com.studentproject.entity.Submission;
import com.studentproject.entity.User;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.SubmissionRepository;
import com.studentproject.service.SubmissionService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProjectRepository projectRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public SubmissionResponse createSubmission(CreateSubmissionRequest request) {
        User student = authContextUtil.getCurrentUser();
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Submission submission = Submission.builder()
                .project(project)
                .submittedBy(student)
                .fileUrl(request.getFileUrl())
                .remarks(request.getRemarks())
                .build();

        return map(submissionRepository.save(submission));
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return submissionRepository.findByProject(project).stream().map(this::map).toList();
    }

    @Override
    public List<SubmissionResponse> getMySubmissions() {
        User student = authContextUtil.getCurrentUser();
        return submissionRepository.findBySubmittedBy(student).stream().map(this::map).toList();
    }

    private SubmissionResponse map(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .projectId(submission.getProject().getId())
                .projectTitle(submission.getProject().getTitle())
                .submittedById(submission.getSubmittedBy().getId())
                .submittedByName(submission.getSubmittedBy().getName())
                .fileUrl(submission.getFileUrl())
                .submittedAt(submission.getSubmittedAt())
                .remarks(submission.getRemarks())
                .build();
    }
}
