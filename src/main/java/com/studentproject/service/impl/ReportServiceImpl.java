package com.studentproject.service.impl;

import com.studentproject.dto.report.ProjectReportResponse;
import com.studentproject.dto.report.StudentReportResponse;
import com.studentproject.entity.Project;
import com.studentproject.entity.User;
import com.studentproject.entity.enums.TaskStatus;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.FeedbackRepository;
import com.studentproject.repository.GroupRepository;
import com.studentproject.repository.MilestoneRepository;
import com.studentproject.repository.ProjectRepository;
import com.studentproject.repository.SubmissionRepository;
import com.studentproject.repository.TaskRepository;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Override
    public ProjectReportResponse getProjectSummary(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return ProjectReportResponse.builder()
                .projectId(project.getId())
                .projectTitle(project.getTitle())
                .totalGroups(groupRepository.countByProject(project))
                .totalTasks(taskRepository.countByProject(project))
                .completedTasks(taskRepository.countByProjectAndStatus(project, TaskStatus.COMPLETED))
                .totalMilestones(milestoneRepository.countByProject(project))
                .completedMilestones(milestoneRepository.countByProjectAndCompleted(project, true))
                .totalSubmissions(submissionRepository.countByProject(project))
                .build();
    }

    @Override
    public StudentReportResponse getStudentPerformance(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return StudentReportResponse.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .assignedTasks(taskRepository.countByAssignedTo(student))
                .completedTasks(taskRepository.countByAssignedToAndStatus(student, TaskStatus.COMPLETED))
                .submissions(submissionRepository.countBySubmittedBy(student))
                .feedbackCount(feedbackRepository.countByStudent(student))
                .build();
    }
}
