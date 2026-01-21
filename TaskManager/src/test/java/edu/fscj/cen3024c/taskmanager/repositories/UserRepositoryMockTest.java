// UserRepositoryMockTest.java
// Tests coverage of UserRepository methods with Mockito

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryMockTest {

    @Mock
    private UserRepository userRepository;

    // Verifies that save returns the persisted entity (mocked, no DB)
    @Test
    public void testSave_ShouldReturnPersistedUser() {
        // Given
        User toSave = new User();
        toSave.setUsername("JohnSmith");
        toSave.setHash("dummyhash123");
        toSave.setSalt("dummysalt");

        User persisted = new User();
        persisted.setId(1);
        persisted.setUsername("JohnSmith");
        persisted.setHash("dummyhash123");
        persisted.setSalt("dummysalt");

        when(userRepository.save(toSave)).thenReturn(persisted);

        // When
        User result = userRepository.save(toSave);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("JohnSmith", result.getUsername());
        verify(userRepository, times(1)).save(toSave);
    }

    // Verifies findById returns a user when present.
    @Test
    public void testFindById_ShouldReturnUser_WhenExists() {
        // Given
        Integer id = 7;
        User existing = new User();
        existing.setId(id);
        existing.setUsername("ExistingUser");

        when(userRepository.findById(id)).thenReturn(Optional.of(existing));

        // When
        Optional<User> actual = userRepository.findById(id);

        // Then
        assertTrue(actual.isPresent());
        assertEquals(id, actual.get().getId());
        assertEquals("ExistingUser", actual.get().getUsername());
        verify(userRepository, times(1)).findById(id);
    }

    // Verifies findById returns empty when the user does not exist.
    @Test
    public void testFindById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Given
        Integer nonExistentId = 999;
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<User> actual = userRepository.findById(nonExistentId);

        // Then
        assertFalse(actual.isPresent());
        verify(userRepository, times(1)).findById(nonExistentId);
    }
}
