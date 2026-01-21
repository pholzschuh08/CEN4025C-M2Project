// TaskService.java
// D. Singletary
// 9/24/25
// Task service for task manager application

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.TaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.exceptions.TaskNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.TaskRepository;
import edu.fscj.cen3024c.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // ===== CRUD Methods with DTO conversion =====

    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return convertToDTO(task);
    }

    // Helper to return raw entity (for deletes or internal use)
    @Transactional(readOnly = true)
    public Task findByIdEntity(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public TaskDTO updateTask(Integer id, Task taskDetails) {
        Task existingTask = findByIdEntity(id);
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setDueDate(taskDetails.getDueDate());
        existingTask.setPriority(taskDetails.getPriority());
        Task updatedTask = taskRepository.save(existingTask);
        return convertToDTO(updatedTask);
    }

    public void deleteById(Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    // ===== NEW: Associate a User with a Task =====
    @Transactional
    public void assignUserToTask(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        task.getUsers().add(user);
        taskRepository.save(task);
    }

    // ===== Entity to DTO Conversion =====
    public TaskDTO convertToDTO(Task task) {
        String priorityLevel = (task.getPriority() != null)
                ? task.getPriority().getLevel().name()
                : null;

        List<String> subtaskTitles = (task.getSubtasks() != null)
                ? task.getSubtasks().stream()
                .map(Subtask::getTitle)
                .collect(Collectors.toList())
                : List.of();

        Set<String> usernames = (task.getUsers() != null)
                ? task.getUsers().stream()
                .map(User::getUsername)
                .collect(Collectors.toSet())
                : Set.of();

        return new TaskDTO(
                task.getId(),                     // include id
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                priorityLevel,
                subtaskTitles,
                usernames
        );
    }
}
