// Subtask.java
// D. Singletary
// 9/10/25
// Subtask entity for task manager application

package edu.fscj.cen3024c.taskmanager.entities;

import edu.fscj.cen3024c.taskmanager.enums.SubtaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "subtasks")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubtaskStatus status = SubtaskStatus.PENDING; // default

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

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public SubtaskStatus getStatus() { return status; }
    public void setStatus(SubtaskStatus status) { this.status = status; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
