package edu.fscj.cen4025c.taskmanager.dto;

import edu.fscj.cen4025c.taskmanager.enums.SubtaskStatus;

public class SubtaskDTO {

    private Integer id;
    private String title;
    private SubtaskStatus status;  // Enum for status
    private Integer taskId;  // Reference to the parent Task

    public SubtaskDTO() {}

    public SubtaskDTO(Integer id, String title, SubtaskStatus status, Integer taskId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.taskId = taskId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubtaskStatus getStatus() {
        return status;
    }

    public void setStatus(SubtaskStatus status) {
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
