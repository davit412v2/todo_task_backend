package com.todo.task.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.todo.task.entity.Task;
import com.todo.task.entity.User;
import com.todo.task.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAllByUserId(User user) {
        return taskRepository.findAllByUserId(user.getId());
    }
}