package com.fitrehberim.service;

import com.fitrehberim.dto.*;
import com.fitrehberim.model.User;
import com.fitrehberim.repository.UserRepository;
import com.fitrehberim.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                      JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .height(request.getHeight())
            .weight(request.getWeight())
            .activityLevel(request.getActivityLevel())
            .fitnessLevel(request.getFitnessLevel())
            .goal(request.getGoal())
            .availableDays(request.getAvailableDays())
            .availableTime(request.getAvailableTime())
            .build();

        userRepository.save(user);

        String token = jwtTokenProvider.createToken(user.getEmail());
        return AuthResponse.builder()
            .token(token)
            .email(user.getEmail())
            .message("Registration successful")
            .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        return AuthResponse.builder()
            .token(token)
            .email(user.getEmail())
            .message("Login successful")
            .build();
    }
}