package com.todo.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.task.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
