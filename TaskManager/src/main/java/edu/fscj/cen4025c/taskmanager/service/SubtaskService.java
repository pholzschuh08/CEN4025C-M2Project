package edu.fscj.cen4025c.taskmanager.service;

import edu.fscj.cen4025c.taskmanager.entity.Subtask;
import edu.fscj.cen4025c.taskmanager.entity.Task;
import edu.fscj.cen4025c.taskmanager.repository.SubtaskRepository;
import edu.fscj.cen4025c.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TaskRepository taskRepository;  // Add TaskRepository to fetch Task by taskId

    public List<Subtask> findAll() {
        return subtaskRepository.findAll();
    }

    public Optional<Subtask> findById(Integer id) {
        return subtaskRepository.findById(id);
    }

    public List<Subtask> findByTaskId(Integer taskId) {
        return subtaskRepository.findByTaskId(taskId);
    }

    // Updated save method to include task association
    public Subtask save(Subtask subtask, Integer taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            subtask.setTask(taskOptional.get());  // Associate the task with the subtask
            return subtaskRepository.save(subtask);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
    }

    public void deleteById(Integer id) {
        subtaskRepository.deleteById(id);
    }
}
