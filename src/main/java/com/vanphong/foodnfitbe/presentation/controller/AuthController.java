package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.AuthService;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.OtpRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody RegisterRequest request) {
        authService.sendOtp(request);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String email) {
        authService.resendOtp(email);
        return ResponseEntity.ok("OTP resent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok("Account created");
    }
}
