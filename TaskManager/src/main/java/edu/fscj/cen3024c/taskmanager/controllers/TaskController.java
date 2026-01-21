// TaskController.java
// D. Singletary
// 9/24/25
// Task controller for task manager application

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.dto.TaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Priority;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.exceptions.PriorityNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.PriorityRepository;
import edu.fscj.cen3024c.taskmanager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private PriorityRepository priorityRepository;

    // ===== READ endpoints return DTOs =====

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    // ===== WRITE endpoints accept entities but return DTOs =====

    @PostMapping
    public TaskDTO createTask(@RequestBody Task task) {
        if (task.getPriority() != null && task.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(task.getPriority().getId())
                    .orElseThrow(() -> new PriorityNotFoundException(task.getPriority().getId()));
            task.setPriority(priority);
        }
        Task savedTask = taskService.save(task);
        return taskService.convertToDTO(savedTask);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Integer id, @RequestBody Task task) {
        if (task.getPriority() != null && task.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(task.getPriority().getId())
                    .orElseThrow(() -> new PriorityNotFoundException(task.getPriority().getId()));
            task.setPriority(priority);
        }
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ===== NEW: Associate a User with a Task =====
    @PostMapping("/{taskId}/users/{userId}")
    public ResponseEntity<Void> assignUserToTask(@PathVariable Integer taskId,
                                                 @PathVariable Integer userId) {
        taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok().build();
    }
}
