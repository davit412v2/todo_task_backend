package com.todo.task.taskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.todo.task.entity.Task;
import com.todo.task.entity.User;
import com.todo.task.repository.TaskRepository;
import com.todo.task.service.TaskService;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    void testCreate() {
        Task task = new Task();
        task.setTitle("Test Task");
        when(taskRepository.save(task)).thenReturn(task);
        Task createdTask = taskService.create(task);
        assertEquals("Test Task", createdTask.getTitle());
    }

    @Test
    void testGetTasksByUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setUser(user); 
        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setUser(user);

        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findAllByUserId(1L)).thenReturn(tasks);
        List<Task> userTasks = taskService.getTasksByUser(user);
        assertEquals(2, userTasks.size());
    }

    @Test
    void testUpdate() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Updated Task");
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(task)).thenReturn(task);
        Task updatedTask = taskService.update(task);
        assertEquals("Updated Task", updatedTask.getTitle());
    }

    @Test
    void testFindById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        Task foundTask = taskService.findById(1L);
        assertEquals("Test Task", foundTask.getTitle());
    }

    
    
}
