package edu.fscj.cen4025c.taskmanager.entity;

import edu.fscj.cen4025c.taskmanager.enums.PriorityLevel;
import jakarta.persistence.*;

@Entity
@Table(name = "priority")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING) // Store the enum's name (LOW, MEDIUM, HIGH) as a string in the DB
    @Column(name = "level", nullable = false)
    private PriorityLevel level;

    @Column(name = "description", nullable = false)
    private String description;

    // 1:1 relationship with Task
    @OneToOne
    @JoinColumn(name = "task_id", nullable = false, unique = true) // Ensures 1:1 relationship
    private Task task;

    // Constructors
    public Priority() {}

    public Priority(PriorityLevel level, Task task) {
        this.level = level;
        this.description = level.getDescription(); // Automatically set description based on level
        this.task = task;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PriorityLevel getLevel() {
        return level;
    }

    public void setLevel(PriorityLevel level) {
        this.level = level;
        this.description = level.getDescription(); // Automatically update description when level changes
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String escription) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
