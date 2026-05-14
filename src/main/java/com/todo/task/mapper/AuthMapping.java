package com.todo.task.mapper;

import com.todo.task.dto.auth.AuthRequestDto;
import com.todo.task.dto.user.UserRequestDTO;
import com.todo.task.entity.User;

public class AuthMapping {

    private AuthMapping() {
    }

    
    public static User toEntity(AuthRequestDto authRequestDto) {
        if (authRequestDto == null) {
            return null;
        }
        User user = new User();
        user.setName(authRequestDto.getName());
        user.setEmail(authRequestDto.getEmail());
        user.setPassword(authRequestDto.getPassword());
        return user;
    }
    
}
