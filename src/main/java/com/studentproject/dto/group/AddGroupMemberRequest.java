package com.studentproject.dto.group;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddGroupMemberRequest {

    @NotNull
    private Long studentId;
}
