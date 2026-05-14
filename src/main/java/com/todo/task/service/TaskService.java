package com.todo.task.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.todo.task.entity.Task;
import com.todo.task.entity.User;
import com.todo.task.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findAllByUserId(user.getId());
    }

    public Task update(Task task) {
        if (task.getId() == null) {
            throw new RuntimeException("Task ID must not be null for update.");
        }
        if (!taskRepository.existsById(task.getId())) {
            throw new RuntimeException("Task not found with id: " + task.getId());
        }
        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }
}