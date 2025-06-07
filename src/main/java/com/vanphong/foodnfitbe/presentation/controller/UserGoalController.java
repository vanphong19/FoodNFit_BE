package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.UserGoalService;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserGoalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-goal")
@RequiredArgsConstructor
public class UserGoalController {
    private final UserGoalService userGoalService;
    @GetMapping("/getLatest")
    public ResponseEntity<UserGoalResponse> getLatest() {
        UserGoalResponse userGoalResponse = userGoalService.getUserGoalLatest();
        return ResponseEntity.ok(userGoalResponse);
    }
}
