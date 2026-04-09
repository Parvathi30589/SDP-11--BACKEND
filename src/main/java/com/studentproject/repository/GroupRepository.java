package com.studentproject.repository;

import com.studentproject.entity.Group;
import com.studentproject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByProject(Project project);
    long countByProject(Project project);
}
