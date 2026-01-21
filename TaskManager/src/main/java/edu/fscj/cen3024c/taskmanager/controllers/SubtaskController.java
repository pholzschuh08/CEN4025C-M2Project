// SubtaskController.java
// D. Singletary
// 9/24/25
// Subtask controller for task manager application

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.dto.SubtaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import edu.fscj.cen3024c.taskmanager.services.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    // ===== READ endpoints return DTOs =====

    @GetMapping
    public List<SubtaskDTO> getAllSubtasks() {
        return subtaskService.findAll();
    }

    @GetMapping("/{id}")
    public SubtaskDTO getSubtaskById(@PathVariable Integer id) {
        return subtaskService.findById(id);
    }

    // ===== WRITE endpoints accept DTOs and return DTOs =====

    @PostMapping
    public SubtaskDTO createSubtask(@RequestBody SubtaskDTO dto) {
        Subtask subtask = subtaskService.fromDTO(dto);  // build entity with Task reference
        Subtask savedSubtask = subtaskService.save(subtask);
        return subtaskService.convertToDTO(savedSubtask);
    }

    @PutMapping("/{id}")
    public SubtaskDTO updateSubtask(@PathVariable Integer id, @RequestBody SubtaskDTO dto) {
        return subtaskService.updateSubtask(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Integer id) {
        subtaskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
