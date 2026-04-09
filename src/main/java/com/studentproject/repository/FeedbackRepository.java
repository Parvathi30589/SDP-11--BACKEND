package com.studentproject.repository;

import com.studentproject.entity.Feedback;
import com.studentproject.entity.Project;
import com.studentproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProject(Project project);
    List<Feedback> findByStudent(User student);
    long countByStudent(User student);
}
