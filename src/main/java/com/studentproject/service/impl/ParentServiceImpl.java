package com.studentproject.service.impl;

import com.studentproject.dto.parent.LinkParentStudentRequest;
import com.studentproject.dto.parent.ParentProgressResponse;
import com.studentproject.dto.parent.ParentStudentResponse;
import com.studentproject.entity.ParentStudentLink;
import com.studentproject.entity.User;
import com.studentproject.entity.enums.Role;
import com.studentproject.entity.enums.TaskStatus;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.exception.UnauthorizedException;
import com.studentproject.exception.ValidationException;
import com.studentproject.repository.FeedbackRepository;
import com.studentproject.repository.ParentStudentLinkRepository;
import com.studentproject.repository.SubmissionRepository;
import com.studentproject.repository.TaskRepository;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.ParentService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentStudentLinkRepository parentStudentLinkRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final SubmissionRepository submissionRepository;
    private final FeedbackRepository feedbackRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public void linkParentToStudent(LinkParentStudentRequest request) {
        User parent = userRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (parent.getRole() != Role.PARENT || student.getRole() != Role.STUDENT) {
            throw new ValidationException("Invalid parent/student roles");
        }

        if (parentStudentLinkRepository.findByParentAndStudent(parent, student).isPresent()) {
            throw new ValidationException("Parent-student link already exists");
        }

        ParentStudentLink link = ParentStudentLink.builder()
                .parent(parent)
                .student(student)
                .build();
        parentStudentLinkRepository.save(link);
    }

    @Override
    public List<ParentStudentResponse> getMyStudents() {
        User parent = authContextUtil.getCurrentUser();
        return parentStudentLinkRepository.findByParent(parent).stream()
                .map(link -> ParentStudentResponse.builder()
                        .studentId(link.getStudent().getId())
                        .studentName(link.getStudent().getName())
                        .studentEmail(link.getStudent().getEmail())
                        .build())
                .toList();
    }

    @Override
    public ParentProgressResponse getStudentProgress(Long studentId) {
        User parent = authContextUtil.getCurrentUser();
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        boolean linked = parentStudentLinkRepository.findByParentAndStudent(parent, student).isPresent();
        if (!linked) {
            throw new UnauthorizedException("You are not linked to this student");
        }

        return ParentProgressResponse.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .totalTasks(taskRepository.countByAssignedTo(student))
                .completedTasks(taskRepository.countByAssignedToAndStatus(student, TaskStatus.COMPLETED))
                .totalSubmissions(submissionRepository.countBySubmittedBy(student))
                .totalFeedbacks(feedbackRepository.countByStudent(student))
                .build();
    }
}
