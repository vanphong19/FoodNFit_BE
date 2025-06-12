package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.UserService;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<UserResponse>> getAll(
            @RequestParam(defaultValue = "") String search,  // Mặc định là chuỗi rỗng
            @RequestParam(defaultValue = "") Boolean gender,  // Mặc định là true
            @RequestParam(defaultValue = "") Boolean block,  // Mặc định là true
            @RequestParam(defaultValue = "0") Integer page,  // Mặc định là 0
            @RequestParam(defaultValue = "10") Integer size,  // Mặc định là 10
            @RequestParam(defaultValue = "id") String sortBy,  // Mặc định là "id"
            @RequestParam(defaultValue = "asc") String sortDir  // Mặc định là "asc"
    ) {
        // Nếu không có giá trị `block`, nó sẽ tự động là null
        UserSearchCriteria criteria = new UserSearchCriteria();
        criteria.setSearch(search);
        criteria.setGender(gender);
        criteria.setBlock(block); // Nếu block không được cung cấp, giá trị mặc định sẽ là null
        criteria.setPage(page);
        criteria.setSize(size);
        criteria.setSortBy(sortBy);
        criteria.setSortDir(sortDir);

        // Gọi service để lấy danh sách người dùng
        Page<UserResponse> userResponses = userService.getAllUsers(criteria);
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
    @GetMapping("/count-last-month")
    public ResponseEntity<Long> getCountUserLastMonth() {
        Long count = userService.countUsersCreateAtLastMonth();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountUser() {
        Long count = userService.countUsers();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count-this-month")
    public ResponseEntity<Long> getCountUserThisMonth() {
        Long count = userService.countUsersCreateAtThisMonth();
        return ResponseEntity.ok(count);
    }
}
