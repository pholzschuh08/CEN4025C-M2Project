// SubtaskService.java
// D. Singletary
// 9/24/25
// Subtask service for task manager application

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.SubtaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.enums.SubtaskStatus;
import edu.fscj.cen3024c.taskmanager.exceptions.SubtaskNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.SubtaskRepository;
import edu.fscj.cen3024c.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, TaskRepository taskRepository) {
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }

    // ===== CRUD Methods with DTO conversion =====

    @Transactional(readOnly = true)
    public List<SubtaskDTO> findAll() {
        return subtaskRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubtaskDTO findById(Integer id) {
        Subtask subtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new SubtaskNotFoundException(id));
        return convertToDTO(subtask);
    }

    public Subtask save(Subtask subtask) {
        // Default status if not provided
        if (subtask.getStatus() == null) {
            subtask.setStatus(SubtaskStatus.PENDING);
        }
        return subtaskRepository.save(subtask);
    }

    @Transactional
    public SubtaskDTO updateSubtask(Integer id, SubtaskDTO dto) {
        Subtask existingSubtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new SubtaskNotFoundException(id));

        existingSubtask.setTitle(dto.getTitle());

        // Convert String to Enum safely
        SubtaskStatus status = (dto.getStatus() != null)
                ? SubtaskStatus.valueOf(dto.getStatus())
                : SubtaskStatus.PENDING;
        existingSubtask.setStatus(status);

        // Update Task if taskId is provided
        if (dto.getTaskId() != null) {
            Task task = taskRepository.findById(dto.getTaskId())
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            existingSubtask.setTask(task);
        }

        Subtask updatedSubtask = subtaskRepository.save(existingSubtask);
        return convertToDTO(updatedSubtask);
    }

    public void deleteById(Integer id) {
        if (!subtaskRepository.existsById(id)) {
            throw new SubtaskNotFoundException(id);
        }
        subtaskRepository.deleteById(id);
    }

    // ===== Conversion Methods =====

    // Entity -> DTO
    public SubtaskDTO convertToDTO(Subtask subtask) {
        Integer taskId = (subtask.getTask() != null) ? subtask.getTask().getId() : null;

        return new SubtaskDTO(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getStatus().name(),
                taskId
        );
    }

    // DTO -> Entity
    public Subtask fromDTO(SubtaskDTO dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        SubtaskStatus status = (dto.getStatus() != null)
                ? SubtaskStatus.valueOf(dto.getStatus())
                : SubtaskStatus.PENDING;

        return new Subtask(
                dto.getTitle(),
                status,
                task
        );
    }
}
