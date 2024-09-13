package com.backend.task.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String username;

    @Column()
    @NotBlank(message = "password is required")
    @Size(min = 1,message = "Name must be between 2 and 50 characters")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    public Users() {
    }

    public Users(String password,String username) {
        this.password = password;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tasks=" + tasks.size() +
                '}';
    }
}


