package com.studentproject.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageResponse {
    private Long id;
    private Long groupId;
    private Long senderId;
    private String senderName;
    private String message;
    private LocalDateTime sentAt;
}
