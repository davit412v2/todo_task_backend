package com.todo.task.UserService;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo.task.entity.User;
import com.todo.task.repository.UserRepository;
import com.todo.task.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void testExistsByEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("test@test.com");

        assert(exists);

    }


    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assert(createdUser.getEmail().equals("test@test.com"));
        assert(createdUser.getPassword().equals("encodedPassword"));
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail("test@test.com");

        assert(foundUser.getEmail().equals("test@test.com"));
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assert(foundUser.getId().equals(1L));
    }

    @Test
    void testFindOrCreateGoogleUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test User");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        User foundUser = userService.findOrCreateGoogleUser("test@test.com", "Test User");

        assert(foundUser.getEmail().equals("test@test.com"));
        assert(foundUser.getName().equals("Test User"));
    }
}