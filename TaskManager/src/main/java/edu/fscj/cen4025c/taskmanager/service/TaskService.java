package edu.fscj.cen4025c.taskmanager.service;

import edu.fscj.cen4025c.taskmanager.dto.TaskDTO;
import edu.fscj.cen4025c.taskmanager.entity.Priority;
import edu.fscj.cen4025c.taskmanager.entity.Task;
import edu.fscj.cen4025c.taskmanager.enums.PriorityLevel;
import edu.fscj.cen4025c.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Convert TaskDTO to Task entity
    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());

        // If priorityLevel is provided, map it back to Priority entity
        if (taskDTO.getPriorityLevel() != null) {
            Priority priority = new Priority();
            priority.setLevel(PriorityLevel.valueOf(taskDTO.getPriorityLevel()));
            priority.setDescription(taskDTO.getPriorityDescription());
            task.setPriority(priority);
        }

        return task;
    }

    // Convert Task entity to TaskDTO
    private TaskDTO convertToDTO(Task task) {
        String priorityLevel = null;
        String priorityDescription = null;

        // Check if the task has an associated priority
        if (task.getPriority() != null) {
            priorityLevel = task.getPriority().getLevel().name();
            priorityDescription = task.getPriority().getDescription();
        }

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                priorityLevel,
                priorityDescription
        );
    }

    // Find all tasks and return as TaskDTOs
    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Find a task by ID and convert it to TaskDTO
    public Optional<TaskDTO> findById(Integer id) {
        return taskRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Save or update a task
    public TaskDTO save(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO); // Convert DTO to entity
        Task savedTask = taskRepository.save(task); // Save entity to database
        return convertToDTO(savedTask); // Convert back to DTO and return
    }

    // Delete task by ID
    public void deleteById(Integer id) {
        taskRepository.deleteById(id);
    }
}
