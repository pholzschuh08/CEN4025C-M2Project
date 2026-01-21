// UserServiceTest.java
// Tests coverage of UserService methods

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.UserDTO;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // Tests findAll()
    @Test
    void findAll_ShouldReturnUserDTOList() {
        // Given
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("JohnSmith");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("JaneDoe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<UserDTO> result = userService.findAll();

        // Then
        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("JohnSmith");
        verify(userRepository, times(1)).findAll();
    }

    // Tests save(UserDTO)
    @Test
    void saveUserDTO_ShouldSaveUserAndReturnUserEntity() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("JohnSmith");
        userDTO.setPassword("secret123");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setUsername(userDTO.getUsername());
        savedUser.setTasks(Set.of());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.save(userDTO); // ✅ returns User entity

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("JohnSmith");
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Tests retrieval by ID returning UserDTO
    @Test
    void findById_ShouldReturnUserDTO_WhenUserExists() {
        // Given
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setUsername("JohnSmith");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserDTO result = userService.findById(userId); // ✅ service returns DTO here

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("JohnSmith");
        verify(userRepository, times(1)).findById(userId);
    }

    // Tests deleteById()
    @Test
    void deleteById_ShouldDeleteUser() {
        // Given
        Integer userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteById(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
