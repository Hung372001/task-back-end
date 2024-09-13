package com.backend.task.dto;



import java.util.List;
import com.backend.task.model.Task;

public class UserDto {
    private int id;
    private String username;
    private List<Task> tasks;

    public UserDto() {
    }

    public UserDto(int id,String username, List<Task> tasks) {
        this.id=id;
        this.username = username;
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
