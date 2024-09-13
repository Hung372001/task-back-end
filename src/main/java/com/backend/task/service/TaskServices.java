package com.backend.task.service;

import com.backend.task.dto.TaskDto;
import com.backend.task.model.Task;

import java.util.List;

public interface TaskServices {
    Task createTask(Task task,int userId);
    List<TaskDto> getTasks();
}
