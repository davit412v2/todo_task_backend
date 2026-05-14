package com.todo.task.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.task.entity.User;
import com.todo.task.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        final String emailLowerCase = toLowerCase(email);
        return userRepository.existsByEmail(emailLowerCase);
    }

    public User create(User user) {
        final String emailLowerCase = toLowerCase(user.getEmail());
        user.setEmail(emailLowerCase);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        final String emailLowerCase = toLowerCase(email);
        return userRepository.findByEmail(emailLowerCase)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findOrCreateGoogleUser(String email, String name) {
        final String emailLowerCase = toLowerCase(email);
        return userRepository.findByEmail(emailLowerCase)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(emailLowerCase);
                    newUser.setName(name);
                    newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); 
                    return userRepository.save(newUser);
                });
    }

    private String toLowerCase(String email) {
        return email.toLowerCase();
    }
}
