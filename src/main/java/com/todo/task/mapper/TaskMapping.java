package com.todo.task.mapper;

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

    public static Task toEntity(TaskResponseDTO taskResponseDTO) {
        if (taskResponseDTO == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskResponseDTO.getId());
        task.setTitle(taskResponseDTO.getTitle());
        task.setDescription(taskResponseDTO.getDescription());
        task.setCompleted(taskResponseDTO.isCompleted());
        if (taskResponseDTO.getUserId() != null) {
            task.setUser(new User());
            task.getUser().setId(taskResponseDTO.getUserId());
        }
        return task;
    }
}
