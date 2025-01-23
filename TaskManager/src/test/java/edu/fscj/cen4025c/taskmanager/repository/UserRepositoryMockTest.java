// UserRepositoryMockTest.java
// tests coverage of UserRepository methods using Mockito

package edu.fscj.cen4025c.taskmanager.repository;

import edu.fscj.cen4025c.taskmanager.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryMockTest {

    @Mock
    private UserRepository userRepository;

    // Tests findByUsername method
    @Test
    public void testFindByUsername_ShouldReturnUser_WhenUsernameExists() {
        // Given
        String username = "JohnSmith";
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        // When
        User actualUser = userRepository.findByUsername(username);

        // Then
        assertNotNull(actualUser, "User should be found");
        assertEquals(expectedUser.getId(), actualUser.getId(), "User ID should match");
        assertEquals(expectedUser.getUsername(), actualUser.getUsername(), "Username should match");

        verify(userRepository, times(1)).findByUsername(username);
    }

    // Tests the findByEmail method
    @Test
    public void testFindByEmail_ShouldReturnUser_WhenEmailExists() {
        // Given
        String email = "john.smith@example.com";
        User expectedUser = new User();
        expectedUser.setId(2);
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // When
        User actualUser = userRepository.findByEmail(email);

        // Then
        assertNotNull(actualUser, "User should be found");
        assertEquals(expectedUser.getId(), actualUser.getId(), "User ID should match");
        assertEquals(expectedUser.getEmail(), actualUser.getEmail(), "Email should match");

        verify(userRepository, times(1)).findByEmail(email);
    }

    // Verifies findById returns empty Optional when user ID does not exist
    @Test
    public void testFindById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Given
        Integer nonExistentId = 999;
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<User> actualUser = userRepository.findById(nonExistentId);

        // Then
        assertFalse(actualUser.isPresent(), "No user should be found with non-existent ID");

        verify(userRepository, times(1)).findById(nonExistentId);
    }
}
