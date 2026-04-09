package com.studentproject.service;

import com.studentproject.dto.submission.CreateSubmissionRequest;
import com.studentproject.dto.submission.SubmissionResponse;

import java.util.List;

public interface SubmissionService {
    SubmissionResponse createSubmission(CreateSubmissionRequest request);
    List<SubmissionResponse> getSubmissionsByProject(Long projectId);
    List<SubmissionResponse> getMySubmissions();
}
