package com.studentproject.repository;

import com.studentproject.entity.Milestone;
import com.studentproject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByProject(Project project);
    long countByProject(Project project);
    long countByProjectAndCompleted(Project project, Boolean completed);
}
