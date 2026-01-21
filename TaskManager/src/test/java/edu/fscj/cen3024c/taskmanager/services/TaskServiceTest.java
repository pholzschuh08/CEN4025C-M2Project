// TaskServiceTest.java

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.TaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.repositories.TaskRepository;
import edu.fscj.cen3024c.taskmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private UserRepository userRepository;

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
        assertThat(result.get(0).getTitle()).isEqualTo("Task 1");
        verify(taskRepository, times(1)).findAll();
    }

    // Tests save(Task)
    @Test
    void save_ShouldPersistTaskEntity() {
        // Given
        Task taskToSave = new Task();
        taskToSave.setTitle("New Task");
        taskToSave.setStatus("Pending");
        taskToSave.setDueDate(java.time.LocalDate.now().plusDays(7).toString());

        Task savedTask = new Task();
        savedTask.setId(1);
        savedTask.setTitle(taskToSave.getTitle());
        savedTask.setStatus(taskToSave.getStatus());
        savedTask.setDueDate(taskToSave.getDueDate());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // When
        Task result = taskService.save(taskToSave);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("New Task");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Tests retrieval by ID returning TaskDTO
    @Test
    void findById_ShouldReturnTaskDTO_WhenTaskExists() {
        // Given
        Integer taskId = 1;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Existing Task");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        TaskDTO result = taskService.findById(taskId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Existing Task");
        verify(taskRepository, times(1)).findById(taskId);
    }

    // Tests deleteById()
    @Test
    void deleteById_ShouldDeleteTask() {
        // Given
        Integer taskId = 1;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        // When
        taskService.deleteById(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    // Tests deleteById throws exception when task does not exist
    @Test
    void deleteById_ShouldThrow_WhenTaskDoesNotExist() {
        // Given
        Integer taskId = 99;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> taskService.deleteById(taskId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(String.valueOf(taskId));
    }
}
