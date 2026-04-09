package com.studentproject.dto.task;

import com.studentproject.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {

    @NotNull
    private TaskStatus status;
}
