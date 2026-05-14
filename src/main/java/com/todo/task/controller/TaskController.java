package com.todo.task.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo.task.dto.task.TaskRequestDTO;
import com.todo.task.dto.task.TaskResponseDTO;
import com.todo.task.entity.Task;
import com.todo.task.entity.User;
import com.todo.task.mapper.TaskMapping;
import com.todo.task.service.TaskService;
import com.todo.task.service.UserService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable("user_id") Long userId) {
        try {
            User user = userService.findById(userId);
            List<Task> tasks = taskService.getTasksByUser(user);

            List<TaskResponseDTO> taskResponseDTOs = tasks.stream()
                    .map(TaskMapping::toResponseDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(taskResponseDTOs);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            User user = userService.findById(taskRequestDTO.getUserId());

            Task task = TaskMapping.toEntity(taskRequestDTO, user);
            Task createdTask = taskService.create(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapping.toResponseDTO(createdTask));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        try {
            User user = userService.findById(taskRequestDTO.getUserId());
            Task task = TaskMapping.toEntity(taskRequestDTO, user);
            task.setId(id);
            Task updatedTask = taskService.update(task);
            return ResponseEntity.ok(TaskMapping.toResponseDTO(updatedTask));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponseDTO> markTaskAsCompleted(@PathVariable("id") Long id) {
        try {
            Task task = taskService.findById(id);
            task.setCompleted(true);
            Task updatedTask = taskService.update(task);
            return ResponseEntity.ok(TaskMapping.toResponseDTO(updatedTask));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        try {
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}