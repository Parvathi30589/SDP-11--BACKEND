package com.studentproject.service.impl;

import com.studentproject.dto.feedback.CreateFeedbackRequest;
import com.studentproject.dto.feedback.FeedbackResponse;
import com.studentproject.entity.Feedback;
import com.studentproject.entity.Project;
import com.studentproject.entity.User;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.FeedbackRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.FeedbackService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public FeedbackResponse createFeedback(CreateFeedbackRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        User teacher = authContextUtil.getCurrentUser();

        Feedback feedback = Feedback.builder()
                .project(project)
                .student(student)
                .givenBy(teacher)
                .comments(request.getComments())
                .grade(request.getGrade())
                .build();

        return map(feedbackRepository.save(feedback));
    }

    @Override
    public List<FeedbackResponse> getFeedbackByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return feedbackRepository.findByProject(project).stream().map(this::map).toList();
    }

    @Override
    public List<FeedbackResponse> getFeedbackByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return feedbackRepository.findByStudent(student).stream().map(this::map).toList();
    }

    private FeedbackResponse map(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .projectId(feedback.getProject().getId())
                .projectTitle(feedback.getProject().getTitle())
                .studentId(feedback.getStudent().getId())
                .studentName(feedback.getStudent().getName())
                .givenById(feedback.getGivenBy().getId())
                .givenByName(feedback.getGivenBy().getName())
                .comments(feedback.getComments())
                .grade(feedback.getGrade())
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}
