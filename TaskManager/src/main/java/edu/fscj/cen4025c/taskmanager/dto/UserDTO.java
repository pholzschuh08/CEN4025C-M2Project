package edu.fscj.cen4025c.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Set<Integer> taskIds;

    // Constructors
    public UserDTO() {}

    public UserDTO(Integer id, String username, String email, Set<Integer> taskIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.taskIds = taskIds;
    }

    public UserDTO(Integer id, String username, String email, String password, Set<Integer> taskIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.taskIds = taskIds;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Set<Integer> taskIds) {
        this.taskIds = taskIds;
    }
}
