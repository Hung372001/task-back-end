package com.backend.task.service;

import com.backend.task.dto.UserDto;
import com.backend.task.model.Users;

import java.util.Optional;

public interface UserServices {
    Users createUser(Users user);
    UserDto getUser(int userId);
}
