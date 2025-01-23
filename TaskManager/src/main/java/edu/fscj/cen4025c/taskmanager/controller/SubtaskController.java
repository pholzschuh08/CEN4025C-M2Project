package edu.fscj.cen4025c.taskmanager.controller;

import edu.fscj.cen4025c.taskmanager.dto.SubtaskDTO;
import edu.fscj.cen4025c.taskmanager.entity.Subtask;
import edu.fscj.cen4025c.taskmanager.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    // Convert Subtask entity to SubtaskDTO
    private SubtaskDTO convertToDTO(Subtask subtask) {
        return new SubtaskDTO(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getStatus(),
                subtask.getTask().getId()
        );
    }

    // Convert SubtaskDTO to Subtask entity
    private Subtask convertToEntity(SubtaskDTO subtaskDTO) {
        Subtask subtask = new Subtask();
        subtask.setId(subtaskDTO.getId());
        subtask.setTitle(subtaskDTO.getTitle());
        subtask.setStatus(subtaskDTO.getStatus());
        // The task will be set in the service using taskId
        return subtask;
    }

    // Get all subtasks
    @GetMapping
    public List<SubtaskDTO> getAllSubtasks() {
        List<Subtask> subtasks = subtaskService.findAll();
        return subtasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get subtasks by Task ID
    @GetMapping("/task/{taskId}")
    public List<SubtaskDTO> getSubtasksByTaskId(@PathVariable Integer taskId) {
        List<Subtask> subtasks = subtaskService.findByTaskId(taskId);
        return subtasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a single subtask by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDTO> getSubtaskById(@PathVariable Integer id) {
        Optional<Subtask> subtask = subtaskService.findById(id);
        return subtask.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new subtask
    @PostMapping
    public ResponseEntity<SubtaskDTO> createSubtask(@RequestBody SubtaskDTO subtaskDTO) {
        Subtask newSubtask = convertToEntity(subtaskDTO);
        Subtask savedSubtask = subtaskService.save(newSubtask, subtaskDTO.getTaskId()); // Pass taskId
        return ResponseEntity.status(201).body(convertToDTO(savedSubtask));
    }

    // Update an existing subtask
    @PutMapping("/{id}")
    public ResponseEntity<SubtaskDTO> updateSubtask(@PathVariable Integer id, @RequestBody SubtaskDTO subtaskDTO) {
        Optional<Subtask> existingSubtask = subtaskService.findById(id);
        if (existingSubtask.isPresent()) {
            Subtask subtaskToUpdate = convertToEntity(subtaskDTO);
            subtaskToUpdate.setId(id);  // Ensure the correct ID is set
            Subtask updatedSubtask = subtaskService.save(subtaskToUpdate, subtaskDTO.getTaskId()); // Pass taskId
            return ResponseEntity.ok(convertToDTO(updatedSubtask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a subtask by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Integer id) {
        Optional<Subtask> existingSubtask = subtaskService.findById(id);
        if (existingSubtask.isPresent()) {
            subtaskService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
