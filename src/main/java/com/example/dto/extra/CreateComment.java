package com.example.dto.extra;

import jakarta.validation.constraints.NotBlank;

public class CreateComment {
    @NotBlank(message = "task id required")
    private String taskId;
    @NotBlank(message = "message required")
    private String message;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
