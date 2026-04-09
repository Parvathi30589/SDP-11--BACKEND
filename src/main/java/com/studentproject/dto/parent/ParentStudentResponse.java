package com.studentproject.dto.parent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentStudentResponse {
    private Long studentId;
    private String studentName;
    private String studentEmail;
}
