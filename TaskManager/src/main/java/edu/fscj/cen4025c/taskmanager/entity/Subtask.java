package edu.fscj.cen4025c.taskmanager.entity;

import edu.fscj.cen4025c.taskmanager.enums.SubtaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "subtask")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private SubtaskStatus status;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Constructors
    public Subtask() {}

    public Subtask(String title, SubtaskStatus status, Task task) {
        this.title = title;
        this.status = status;
        this.task = task;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
