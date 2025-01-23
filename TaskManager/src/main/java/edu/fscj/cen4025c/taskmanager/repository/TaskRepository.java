package edu.fscj.cen4025c.taskmanager.repository;

import edu.fscj.cen4025c.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> { }