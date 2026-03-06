package com.todo.controller;

import com.todo.dto.ApiResponse;
import com.todo.dto.UserResponse;
import com.todo.security.CustomUserDetails;
import com.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse user = userService.getCurrentUser(userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
