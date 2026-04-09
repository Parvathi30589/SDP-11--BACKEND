package com.studentproject.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGroupRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long projectId;
}
