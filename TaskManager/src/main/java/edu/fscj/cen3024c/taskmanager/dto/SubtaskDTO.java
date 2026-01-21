// SubtaskDTO.java
// D. Singletary
// 9/10/25
// Subtask DTO for task manager application

package edu.fscj.cen3024c.taskmanager.dto;

public class SubtaskDTO {

    private Integer id;
    private String title;
    private String status;   // from SubtaskStatus enum
    private Integer taskId;  // parent Task ID

    // Constructors
    public SubtaskDTO() {}

    public SubtaskDTO(Integer id, String title, String status, Integer taskId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.taskId = taskId;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getTaskId() { return taskId; }
    public void setTaskId(Integer taskId) { this.taskId = taskId; }
}
