package com.studentproject.repository;

import com.studentproject.entity.ChatMessage;
import com.studentproject.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByGroupOrderBySentAtAsc(Group group);
}
