// UserDTO.java
// D. Singletary
// 9/24/25
// DTO for User entity (excludes password from responses, includes task titles)

// DS
// 10/21/25
// added validation

package edu.fscj.cen3024c.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class UserDTO {

    private Integer id;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password; // for inbound writes only

    private Set<String> taskTitles; // names of tasks owned by this user

    // Constructors
    public UserDTO() {}

    public UserDTO(Integer id, String username, Set<String> taskTitles) {
        this.id = id;
        this.username = username;
        this.taskTitles = taskTitles;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getTaskTitles() { return taskTitles; }
    public void setTaskTitles(Set<String> taskTitles) { this.taskTitles = taskTitles; }
}
