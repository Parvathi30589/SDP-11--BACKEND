package com.studentproject.repository;

import com.studentproject.entity.Group;
import com.studentproject.entity.GroupMember;
import com.studentproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup(Group group);
    List<GroupMember> findByStudent(User student);
    Optional<GroupMember> findByGroupAndStudent(Group group, User student);
    boolean existsByGroupAndStudent(Group group, User student);
}
