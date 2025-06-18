package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.UserProfileService;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.MonthlyProfileResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserProfileRequest userProfileRequest) {
        UserProfileResponse userProfileResponse = userProfileService.createUserProfile(userProfileRequest);
        return ResponseEntity.ok("Create user profile successfully");
    }
    @GetMapping("/getLatest")
    public ResponseEntity<UserProfileResponse> getLatest() {
        UserProfileResponse response = userProfileService.findByUserId();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly-stats")
    public ResponseEntity<MonthlyProfileResponse> getMonthlyStatistics(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        // Thêm kiểm tra đầu vào cho year và month nếu cần
        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().build();
        }

        MonthlyProfileResponse response = userProfileService.getMonthlyProfile(year, month);
        return ResponseEntity.ok(response);
    }
}
