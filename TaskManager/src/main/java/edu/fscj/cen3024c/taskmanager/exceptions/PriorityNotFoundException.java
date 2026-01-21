// PriorityNotFoundException.java
// D. Singletary
// 9/10/25
// Exception for handling Priority not found cases

package edu.fscj.cen3024c.taskmanager.exceptions;

public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException(Integer id) {
        super("Priority not found with id " + id);
    }
}
