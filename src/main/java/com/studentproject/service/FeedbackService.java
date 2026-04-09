package com.studentproject.service;

import com.studentproject.dto.feedback.CreateFeedbackRequest;
import com.studentproject.dto.feedback.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse createFeedback(CreateFeedbackRequest request);
    List<FeedbackResponse> getFeedbackByProject(Long projectId);
    List<FeedbackResponse> getFeedbackByStudent(Long studentId);
}
