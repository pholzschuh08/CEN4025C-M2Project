// TaskRepositoryInMemoryTest.java
// Tests coverage of TaskRepository methods using H2

package edu.fscj.cen4025c.taskmanager.repository;

import edu.fscj.cen4025c.taskmanager.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryInMemoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    // Creates Task and verifies save method
    @Test
    public void saveTask_ShouldPersistTaskInDatabase() {
        // Given
        Task newTask = new Task();
        newTask.setTitle("Complete Assignment");
        newTask.setStatus("Pending");
        newTask.setDueDate(LocalDate.now().plusDays(3));

        // When
        Task savedTask = taskRepository.save(newTask);

        // Then
        Task foundTask = entityManager.find(Task.class, savedTask.getId());
        assertThat(foundTask).isEqualTo(savedTask);
    }

    // Persists Task and verifies findById retrieves it correctly
    @Test
    public void findById_ShouldReturnTask_WhenTaskExists() {
        // Given
        Task task = new Task();
        task.setTitle("Write Report");
        task.setStatus("In Progress");
        task.setDueDate(LocalDate.now().plusDays(5));
        entityManager.persistAndFlush(task);

        // When
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        // Then
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle()).isEqualTo("Write Report");
    }

    // Verifies findById returns empty Optional when a task ID is not found
    @Test
    public void findById_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        // Given
        Integer nonExistentId = 999;

        // When
        Optional<Task> foundTask = taskRepository.findById(nonExistentId);

        // Then
        assertThat(foundTask).isEmpty();
    }
}