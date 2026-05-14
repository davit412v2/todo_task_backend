package com.todo.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.todo.task.dto.auth.AuthRequestDto;
import com.todo.task.dto.auth.AuthResponseDto;
import com.todo.task.dto.auth.GoogleAuthRequestDto;
import com.todo.task.entity.User;
import com.todo.task.mapper.AuthMapping;
import com.todo.task.security.GoogleAuthService;
import com.todo.task.security.JwtService;
import com.todo.task.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthService googleAuthService;

    public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder,
            GoogleAuthService googleAuthService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        String findedPassword = userService.findByEmail(email).getPassword();

        if (!passwordEncoder.matches(password, findedPassword)) {
            throw new RuntimeException("Invalid credentials for email: " + email);
        }

        String token = jwtService.generateToken(email);

        AuthResponseDto authResponse = new AuthResponseDto(token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto authRequest) {
        String email = authRequest.getEmail();

        User user = AuthMapping.toEntity(authRequest);

        if (userService.existsByEmail(email)) {
            throw new RuntimeException("Email already in use: " + email);
        }

        userService.create(user);

        String token = jwtService.generateToken(email);

        AuthResponseDto authResponse = new AuthResponseDto(token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponseDto> googleLogin(@RequestBody GoogleAuthRequestDto googleAuthRequest) {
        GoogleIdToken.Payload payload = googleAuthService.verifyToken(googleAuthRequest.getIdToken());
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        User user = userService.findOrCreateGoogleUser(email, name);

        String token = jwtService.generateToken(user.getEmail());

        AuthResponseDto authResponse = new AuthResponseDto(token);

        return ResponseEntity.ok(authResponse);
    }

}
