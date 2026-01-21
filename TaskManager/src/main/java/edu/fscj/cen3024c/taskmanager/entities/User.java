// User.java
// D. Singletary
// 9/10/25
// User entity for task manager application

package edu.fscj.cen3024c.taskmanager.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users") // avoid 'user' in PostgreSQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    // store base64-encoded salt and hash (32 bytes => ~44 chars)
    @Column(nullable = false, length = 44)
    private String salt;

    @Column(nullable = false, length = 44)
    private String hash;

    @ManyToMany(mappedBy = "users")
    private Set<Task> tasks;

    // Constructors
    public User() {}

    public User(String username, String password) {
        this.username = username;
        // password no longer stored directly — handled via hashing service
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public Set<Task> getTasks() { return tasks; }
    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}
