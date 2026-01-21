// SubtaskNotFoundException.java
// D. Singletary
// 9/10/25
// Exception for handling Subtask not found cases

package edu.fscj.cen3024c.taskmanager.exceptions;

public class SubtaskNotFoundException extends RuntimeException {
    public SubtaskNotFoundException(Integer id) {
        super("Subtask not found with id " + id);
    }
}
