// UserRepositoryInMemoryTest.java
// tests coverage of UserRepository methods using H2

package edu.fscj.cen4025c.taskmanager.repository;

import edu.fscj.cen4025c.taskmanager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryInMemoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    // Creates User and verifies findByUsername retrieves it correctly.
    @Test
    public void saveUser_ShouldPersistUserInDatabase() {
        // Given
        User newUser = new User();
        newUser.setUsername("JohnSmith");
        newUser.setEmail("john.doe@example.com");

        // When
        User savedUser = userRepository.save(newUser);

        // Then
        User foundUser = entityManager.find(User.class, savedUser.getId());
        assertThat(foundUser).isEqualTo(savedUser);
    }

    // Persists User and verifies findByEmail retrieves it correctly
    @Test
    public void findByEmail_ShouldReturnUser_WhenEmailExists() {
        // Given
        User user = new User();
        user.setUsername("TomJones");
        user.setEmail("tom.jones@example.com");
        entityManager.persistAndFlush(user);

        // When
        User foundUser = userRepository.findByEmail("tom.jones@example.com");

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("tom.jones@example.com");
    }

    // Verifies findById returns empty Optional when a user ID is not found
    @Test
    public void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Given
        Integer nonExistentId = 999;

        // When
        Optional<User> foundUser = userRepository.findById(nonExistentId);

        // Then
        assertThat(foundUser).isEmpty();
    }
}