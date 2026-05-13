package com.todo.task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.todo.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
   
    List<Task> findAllByUserId(Long userId);
}