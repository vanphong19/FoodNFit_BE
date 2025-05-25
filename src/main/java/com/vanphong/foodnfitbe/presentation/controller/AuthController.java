package com.vanphong.foodnfitbe.presentation.controller;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.vanphong.foodnfitbe.application.service.AuthService;
import com.vanphong.foodnfitbe.application.service.JwtService;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.JpaUserRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.*;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AuthResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CurrentUser currentUser;
    private final JwtService jwtService;
    private final JpaUserRepository userRepo;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("Đã gửi otp");
    }
    @Operation(summary = "Xác minh OTP", security = {})
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verify(@RequestBody OtpVerificationRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok("Xác thực thành công");
    }
    @Operation(summary = "Xác minh OTP", security = {})
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody ResendOtpRequest request) {
        authService.resendOtp(request.email());
        return ResponseEntity.ok("Đã gửi lại mã OTP");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        var claims = jwtService.extractAllClaims(refreshTokenRequest.refreshToken());
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        var user = userRepo.findById(userId).orElseThrow();

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken, user.getId()));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(HttpServletRequest request) {
        UUID userId = currentUser.getUserId(request);
        String role = currentUser.getUserRole(request);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("role", role);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody ChangePasswordRequest request) {
        if (!request.newPassword().equals(request.confirmNewPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới không khớp");
        }
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractAllClaims(token).getSubject();
        authService.changePassword(email, request.oldPassword(), request.newPassword());
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ResendOtpRequest request) {
        authService.forgotPassword(request.email());
        return ResponseEntity.ok("Đã gửi mã OTP đặt lại mật khẩu");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.email(), request.otp(), request.newPassword());
        return ResponseEntity.ok("Đặt lại mật khẩu thành công");
    }
}
