// TaskRepositoryMockTest.java

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryMockTest {

    @Mock
    private TaskRepository taskRepository;

    // Tests findById method
    @Test
    public void testFindById_ShouldReturnTask_WhenTaskExists() {
        // Given
        Integer taskId = 1;
        Task expectedTask = new Task();
        expectedTask.setId(taskId);
        expectedTask.setTitle("Complete Mock Testing");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        // When
        Optional<Task> actualTask = taskRepository.findById(taskId);

        // Then
        assertTrue(actualTask.isPresent(), "Task should be found");
        assertEquals(expectedTask.getId(), actualTask.get().getId(), "Task ID should match");
        assertEquals(expectedTask.getTitle(), actualTask.get().getTitle(), "Task title should match");

        verify(taskRepository, times(1)).findById(taskId);
    }

    // Verifies findById returns empty Optional when task ID does not exist
    @Test
    public void testFindById_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        // Given
        Integer nonExistentId = 999;
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<Task> actualTask = taskRepository.findById(nonExistentId);

        // Then
        assertFalse(actualTask.isPresent(), "No task should be found with non-existent ID");

        verify(taskRepository, times(1)).findById(nonExistentId);
    }
}
