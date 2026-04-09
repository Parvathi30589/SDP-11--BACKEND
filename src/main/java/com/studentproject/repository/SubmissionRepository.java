package com.studentproject.repository;

import com.studentproject.entity.Project;
import com.studentproject.entity.Submission;
import com.studentproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByProject(Project project);
    List<Submission> findBySubmittedBy(User submittedBy);
    long countBySubmittedBy(User submittedBy);
    long countByProject(Project project);
}
