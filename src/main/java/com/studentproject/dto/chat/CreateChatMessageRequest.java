package com.studentproject.dto.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateChatMessageRequest {

    @NotNull
    private Long groupId;

    @NotBlank
    private String message;
}
