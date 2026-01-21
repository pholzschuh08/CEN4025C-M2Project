// Priority.java
// D. Singletary
// 9/10/25
// Priority entity for task manager application

package edu.fscj.cen3024c.taskmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.fscj.cen3024c.taskmanager.enums.PriorityLevel;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PriorityLevel level;

    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    @JsonIgnore // add this to stop JSON infinite recursion / lazy load issue
    private Set<Task> tasks;

    // Constructors
    public Priority() {}

    public Priority(PriorityLevel level) {
        this.level = level;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public PriorityLevel getLevel() { return level; }
    public void setLevel(PriorityLevel level) { this.level = level; }

    public Set<Task> getTasks() { return tasks; }
    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}
