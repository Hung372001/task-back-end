package com.backend.task.controller;

import com.backend.task.dto.TaskDto;
import com.backend.task.service.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @GetMapping()
    public List<TaskDto> getTasks() {
        return taskServices.getTasks();
    }
}
