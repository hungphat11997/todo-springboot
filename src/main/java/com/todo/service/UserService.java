package com.todo.service;

import com.todo.dto.*;
import com.todo.entity.User;
import com.todo.exception.ResourceNotFoundException;
import com.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(User user) {
        return mapToUserResponse(user, true);
    }

    @Transactional
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "ROLE_USER")
                .active(true)
                .build();
        user = userRepository.save(user);
        return mapToUserResponse(user, false);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAllWithTasks().stream()
                .map(u -> mapToUserResponse(u, true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findByIdWithTasks(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToUserResponse(user, true);
    }

    @Transactional
    public UserResponse updateUserByAdmin(Long id, AdminUserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        user = userRepository.save(user);
        return mapToUserResponse(user, true);
    }

    private UserResponse mapToUserResponse(User user, boolean includeTasks) {
        List<TaskResponse> tasks = null;
        if (includeTasks && user.getTasks() != null) {
            tasks = user.getTasks().stream()
                    .map(t -> TaskResponse.builder()
                            .id(t.getId())
                            .title(t.getTitle())
                            .description(t.getDescription())
                            .userId(user.getId())
                            .username(user.getUsername())
                            .createdAt(t.getCreatedAt())
                            .updatedAt(t.getUpdatedAt())
                            .build())
                    .collect(Collectors.toList());
        }
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .tasks(tasks)
                .build();
    }
}
