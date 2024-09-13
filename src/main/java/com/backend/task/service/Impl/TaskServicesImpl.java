package com.backend.task.service.Impl;

import com.backend.task.dto.SimpleUserDto;
import com.backend.task.dto.TaskDto;
import com.backend.task.model.Task;
import com.backend.task.model.Users;
import com.backend.task.repository.TaskRepository;
import com.backend.task.repository.UserRepository;
import com.backend.task.service.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private UserRepository _userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task,int userId) {
        Users user = _userRepository.findById(userId) .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        task.setCreatedDate(new Date());
        task.setUpdatedDate(new Date());

        return taskRepository.save(task);
    }

    @Override
    public List<TaskDto> getTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TaskDto convertToDTO(Task task) {
        SimpleUserDto userDto = new SimpleUserDto(
                task.getUser().getId(),
                task.getUser().getUsername()
        );

        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedDate(),
                task.getUpdatedDate(),
                userDto
        );
    }

}
