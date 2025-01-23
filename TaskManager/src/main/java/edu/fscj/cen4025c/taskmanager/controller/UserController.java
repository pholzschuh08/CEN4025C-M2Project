package edu.fscj.cen4025c.taskmanager.controller;

import edu.fscj.cen4025c.taskmanager.dto.UserDTO;
import edu.fscj.cen4025c.taskmanager.entity.User;
import edu.fscj.cen4025c.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// SLF4J Logger and LoggerFactory
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// SLF4J Profiler
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Declare a Logger instance
    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    // Convert User entity to UserDTO
    private UserDTO convertToDTO(User user) {
        Set<Integer> taskIds = user.getTasks().stream()
                .map(task -> task.getId())
                .collect(Collectors.toSet());

        return new UserDTO(user.getId(), user.getUsername(),
                user.getEmail(), taskIds);
    }

    @CrossOrigin(origins = {"http://example.com", "http://localhost"})
    // Get all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        // start the profiler
        Profiler profiler = new Profiler("createUser");
        profiler.start("Create User");

        // Pass UserDTO directly to the service
        UserDTO savedUser = userService.save(userDTO);
        logger.info("A new user has been added: {}", savedUser.getUsername());

        // stop the profiler
        TimeInstrument ti = profiler.stop();
        ti.print();

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            User updatedUser = userService.save(user);
            logger.info("A user has been updated: {}", user.getUsername());
            return new ResponseEntity<>(convertToDTO(updatedUser), HttpStatus.OK);
        } else {
            logger.error("Update user failed, not found: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}