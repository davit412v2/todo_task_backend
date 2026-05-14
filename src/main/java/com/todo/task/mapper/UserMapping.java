package com.todo.task.mapper;

import com.todo.task.dto.user.UserRequestDTO;
import com.todo.task.dto.user.UserResponseDTO;
import com.todo.task.entity.User;

public class UserMapping {
    
    private UserMapping() {
    }

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }
}
