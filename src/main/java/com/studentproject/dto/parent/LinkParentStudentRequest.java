package com.studentproject.dto.parent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LinkParentStudentRequest {

    @NotNull
    private Long parentId;

    @NotNull
    private Long studentId;
}
