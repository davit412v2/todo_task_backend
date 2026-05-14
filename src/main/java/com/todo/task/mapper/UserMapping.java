package com.todo.task.mapper;

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

    public static User toEntity(UserResponseDTO userResponseDTO) {
        if (userResponseDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userResponseDTO.getId());
        user.setName(userResponseDTO.getName());
        user.setEmail(userResponseDTO.getEmail());
        return user;
    }
}
