package com.todo.service;

import com.todo.dto.TaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.entity.Task;
import com.todo.entity.User;
import com.todo.exception.ResourceNotFoundException;
import com.todo.exception.ForbiddenException;
import com.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks(User user) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse getMyTaskById(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You can only view your own tasks");
        }
        return mapToResponse(task);
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request, User user) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    @Transactional
    public TaskResponse updateMyTask(Long taskId, TaskRequest request, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You can only update your own tasks");
        }
        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    @Transactional
    public void deleteMyTask(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("You can only delete your own tasks");
        }
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasksForAdmin() {
        return taskRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUser().getId())
                .username(task.getUser().getUsername())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
