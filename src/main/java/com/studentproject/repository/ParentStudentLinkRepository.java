package com.studentproject.repository;

import com.studentproject.entity.ParentStudentLink;
import com.studentproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParentStudentLinkRepository extends JpaRepository<ParentStudentLink, Long> {
    List<ParentStudentLink> findByParent(User parent);
    List<ParentStudentLink> findByStudent(User student);
    Optional<ParentStudentLink> findByParentAndStudent(User parent, User student);
}
