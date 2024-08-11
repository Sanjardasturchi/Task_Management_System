package com.example.dto.extra;

import com.example.enums.Priority;

public class UpdateTaskPriority {
    private String taskId;
    private Priority taskPriority;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }
}
