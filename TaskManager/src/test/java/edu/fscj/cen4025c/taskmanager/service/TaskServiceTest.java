// TaskServiceTest.java
// Tests coverage of TaskService methods

package edu.fscj.cen4025c.taskmanager.service;

import edu.fscj.cen4025c.taskmanager.dto.TaskDTO;
import edu.fscj.cen4025c.taskmanager.entity.Task;
import edu.fscj.cen4025c.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    // Tests findAll()
    @Test
    void findAll_ShouldReturnTaskList() {
        // Given
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        // When
        List<TaskDTO> result = taskService.findAll();

        // Then
        assertThat(result).isNotNull().hasSize(2);
        verify(taskRepository, times(1)).findAll();
    }

    // Tests save(TaskDTO)
    @Test
    void saveTaskDTO_ShouldSaveTaskAndReturnTaskDTO() {
        // Given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");
        taskDTO.setStatus("Pending");
        taskDTO.setDueDate(LocalDate.now().plusDays(7));

        Task savedTask = new Task();
        savedTask.setId(1);
        savedTask.setTitle(taskDTO.getTitle());
        savedTask.setStatus(taskDTO.getStatus());
        savedTask.setDueDate(taskDTO.getDueDate());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // When
        TaskDTO result = taskService.save(taskDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(taskDTO.getTitle());
        assertThat(result.getStatus()).isEqualTo(taskDTO.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Tests retrieval by ID
    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        // Given
        Integer taskId = 1;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Existing Task");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        Optional<TaskDTO> result = taskService.findById(taskId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Existing Task");
        verify(taskRepository, times(1)).findById(taskId);
    }

    // Tests deleteById()
    @Test
    void deleteById_ShouldDeleteTask() {
        // Given
        Integer taskId = 1;

        // When
        taskService.deleteById(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}