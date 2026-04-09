package com.studentproject.service;

import com.studentproject.dto.chat.ChatMessageResponse;
import com.studentproject.dto.chat.CreateChatMessageRequest;

import java.util.List;

public interface ChatService {
    ChatMessageResponse sendMessage(CreateChatMessageRequest request);
    List<ChatMessageResponse> getGroupChatHistory(Long groupId);
}
