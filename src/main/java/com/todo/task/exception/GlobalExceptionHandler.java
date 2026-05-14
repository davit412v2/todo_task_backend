package com.todo.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.task.dto.exception.ExceptionResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(RuntimeException ex) {
        ExceptionResponseDto response = new ExceptionResponseDto(ex.getMessage(), "error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }  
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotFoundException(NotFoundException ex) {
        ExceptionResponseDto response = new ExceptionResponseDto(ex.getMessage(), "error");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
