package com.backend.task.controller;

import com.backend.task.dto.ApiResponse;
import com.backend.task.dto.UserDto;
import com.backend.task.model.Task;
import com.backend.task.model.Users;
import com.backend.task.repository.UserRepository;
import com.backend.task.service.Impl.TaskServicesImpl;
import com.backend.task.service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private TaskServicesImpl taskServices;

    @GetMapping
    public void Hello() {
        System.out.println("Hello");
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new ApiResponse("Validation failed", 400, errors));
        }

        Users newUser = userService.createUser(user);
        if (newUser == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("User creation failed", 400));
        }

        return ResponseEntity.ok(new ApiResponse("User created successfully", 201));

    }

    @PostMapping("/{userId}/task")
    public ResponseEntity<?> createTask(@PathVariable int userId, @Valid @RequestBody Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new ApiResponse("Validation failed", 400, errors));
        }

        Task newTask = taskServices.createTask(task, userId);
        if (newTask == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Task creation failed", 400));
        }

        return ResponseEntity.ok(new ApiResponse("Task created successfully", 201));

    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWithTasks(@PathVariable int userId) {
        try {
            UserDto user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400));
        }
    }


}
