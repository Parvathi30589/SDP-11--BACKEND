package com.studentproject.dto.group;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GroupResponse {
    private Long id;
    private String name;
    private Long projectId;
    private String projectTitle;
    private LocalDateTime createdAt;
    private List<Long> memberIds;
}
