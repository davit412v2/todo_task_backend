package com.todo.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo.task.dto.user.UserRequestDTO;
import com.todo.task.entity.User;
import com.todo.task.mapper.UserMapping;
import com.todo.task.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable("email") String email) {
        try {
            return ResponseEntity.ok(userService.findByEmail(email).getName());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User user = UserMapping.toEntity(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user).getName());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}