// UserRepositoryInMemoryTest.java
// Tests coverage of UserRepository methods using H2 (isolated from data.sql)

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryInMemoryTest {

    // Prevent Spring Boot from running main data.sql when using H2 for tests
    @DynamicPropertySource
    static void disableDataSql(DynamicPropertyRegistry registry) {
        registry.add("spring.sql.init.mode", () -> "never");
        registry.add("spring.datasource.initialization-mode", () -> "never");
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    // Persists a User and verifies it can be retrieved by ID
    @Test
    public void saveUser_ShouldPersistUserInDatabase() {
        // Given
        User newUser = new User();
        newUser.setUsername("JohnSmith");
        newUser.setHash("dummyhash123");   // required, not null
        newUser.setSalt("dummysalt");      // required, not null

        // When
        User persisted = entityManager.persistAndFlush(newUser);

        // Then
        Optional<User> found = userRepository.findById(persisted.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(persisted.getId());
        assertThat(found.get().getUsername()).isEqualTo("JohnSmith");
        assertThat(found.get().getHash()).isEqualTo("dummyhash123");
        assertThat(found.get().getSalt()).isEqualTo("dummysalt");
    }

    // Verifies findById returns empty Optional when the user ID is not found
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
