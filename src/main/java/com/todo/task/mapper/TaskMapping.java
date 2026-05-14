package com.todo.task.mapper;

import com.todo.task.dto.task.TaskRequestDTO;
import com.todo.task.dto.task.TaskResponseDTO;
import com.todo.task.entity.Task;
import com.todo.task.entity.User;

public class TaskMapping {
    
    private TaskMapping() {
    }


    public static TaskResponseDTO toResponseDTO(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getUser().getId()
        );
    }

    public static Task toEntity(TaskRequestDTO taskRequestDTO, User user) {
        if (taskRequestDTO == null) {
            return null;
        }
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.isCompleted());
        if (user != null) {
            task.setUser(user);
        }
        return task;
    }
}
