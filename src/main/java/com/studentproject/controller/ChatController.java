package com.studentproject.controller;

import com.studentproject.dto.chat.ChatMessageResponse;
import com.studentproject.dto.chat.CreateChatMessageRequest;
import com.studentproject.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Chat APIs")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Send message", description = "Student only")
    @ApiResponse(responseCode = "200", description = "Message sent")
    public ResponseEntity<ChatMessageResponse> sendMessage(@Valid @RequestBody CreateChatMessageRequest request) {
        return ResponseEntity.ok(chatService.sendMessage(request));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get chat history by group")
    @ApiResponse(responseCode = "200", description = "Chat history fetched")
    public ResponseEntity<List<ChatMessageResponse>> getGroupHistory(@PathVariable Long groupId) {
        return ResponseEntity.ok(chatService.getGroupChatHistory(groupId));
    }
}
