package com.studentproject.service.impl;

import com.studentproject.dto.chat.ChatMessageResponse;
import com.studentproject.dto.chat.CreateChatMessageRequest;
import com.studentproject.entity.ChatMessage;
import com.studentproject.entity.Group;
import com.studentproject.entity.User;
import com.studentproject.exception.ResourceNotFoundException;
import com.studentproject.repository.ChatMessageRepository;
import com.studentproject.repository.GroupRepository;
import com.studentproject.service.ChatService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final GroupRepository groupRepository;
    private final AuthContextUtil authContextUtil;

    @Override
    public ChatMessageResponse sendMessage(CreateChatMessageRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        User sender = authContextUtil.getCurrentUser();
        ChatMessage message = ChatMessage.builder()
                .group(group)
                .sender(sender)
                .message(request.getMessage())
                .build();

        return map(chatMessageRepository.save(message));
    }

    @Override
    public List<ChatMessageResponse> getGroupChatHistory(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return chatMessageRepository.findByGroupOrderBySentAtAsc(group).stream().map(this::map).toList();
    }

    private ChatMessageResponse map(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .groupId(chatMessage.getGroup().getId())
                .senderId(chatMessage.getSender().getId())
                .senderName(chatMessage.getSender().getName())
                .message(chatMessage.getMessage())
                .sentAt(chatMessage.getSentAt())
                .build();
    }
}
