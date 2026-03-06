package com.todo.controller;

import com.todo.dto.*;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    // ========== USER MANAGEMENT ==========

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AdminUserUpdateRequest request) {
        UserResponse user = userService.updateUserByAdmin(id, request);
        return ResponseEntity.ok(ApiResponse.success("User updated", user));
    }

    // ========== VIEW ALL TASKS ==========

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasksForAdmin();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponse> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }
}
