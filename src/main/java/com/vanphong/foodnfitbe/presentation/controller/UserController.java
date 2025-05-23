package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.UserService;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ResponseEntity.ok(userResponses);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<UserResponse> remove(@PathVariable UUID id) {
        UserResponse response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/lock/{id}")
    public ResponseEntity<UserResponse> lock(@PathVariable UUID id) {
        UserResponse response = userService.blockUser(id);
        return ResponseEntity.ok(response);
    }
}
