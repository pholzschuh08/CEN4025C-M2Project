// TaskRespositoryInMemoryTest.java

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"  // skip data.sql
})
public class TaskRepositoryInMemoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void saveTask_ShouldPersistTaskInDatabase() {
        Task task = new Task();
        task.setTitle("Complete Assignment");
        task.setStatus("Pending");
        task.setDueDate(java.time.LocalDate.now().plusDays(3).toString());

        Task savedTask = taskRepository.save(task);
        Task found = entityManager.find(Task.class, savedTask.getId());
        assertThat(found).isEqualTo(savedTask);
    }

    @Test
    void findById_ShouldReturnTask_WhenExists() {
        Task task = new Task();
        task.setTitle("Write Report");
        task.setStatus("In Progress");
        task.setDueDate(java.time.LocalDate.now().toString());
        entityManager.persistAndFlush(task);

        Optional<Task> found = taskRepository.findById(task.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Write Report");
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        assertThat(taskRepository.findById(999)).isEmpty();
    }
}
