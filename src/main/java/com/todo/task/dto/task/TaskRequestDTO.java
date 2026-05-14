package com.todo.task.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    private String title;
    private String description;
    private boolean completed;
    private Long userId;
}
