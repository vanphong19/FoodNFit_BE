package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.UserProfileService;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
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
}
