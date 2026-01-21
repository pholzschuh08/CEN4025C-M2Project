// TaskDTO.java
// D. Singletary
// 9/24/25
// DTO for Task entity (includes subtasks and assigned users)

package edu.fscj.cen3024c.taskmanager.dto;

import java.util.List;
import java.util.Set;

public class TaskDTO {

    private Integer id;
    private String title;
    private String description;
    private String status;
    private String dueDate;           // changed from LocalDate to String
    private String priorityLevel;
    private List<String> subtaskTitles;
    private Set<String> usernames;    // associated users

    // Constructors
    public TaskDTO() {}

    public TaskDTO(Integer id, String title, String description, String status,
                   String dueDate, String priorityLevel,
                   List<String> subtaskTitles, Set<String> usernames) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.priorityLevel = priorityLevel;
        this.subtaskTitles = subtaskTitles;
        this.usernames = usernames;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }

    public List<String> getSubtaskTitles() { return subtaskTitles; }
    public void setSubtaskTitles(List<String> subtaskTitles) { this.subtaskTitles = subtaskTitles; }

    public Set<String> getUsernames() { return usernames; }
    public void setUsernames(Set<String> usernames) { this.usernames = usernames; }
}
