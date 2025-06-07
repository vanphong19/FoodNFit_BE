package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.AuthService;
import com.vanphong.foodnfitbe.application.service.JwtService;
import com.vanphong.foodnfitbe.application.service.RequestHelperService;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserJpaRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.*;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AuthResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final CurrentUser currentUser;
    private final JwtService jwtService;
    private final UserJpaRepository userRepo;
    private final RequestHelperService requestHelperService;

    private void logRequest(String userId, String traceId, String clientIp, String endpoint, String httpMethod,
                            String action, Object... additionalKVs) {
        MDC.put("userId", userId);
        MDC.put("traceId", traceId);
        MDC.put("clientIp", clientIp);
        MDC.put("endpoint", endpoint);
        MDC.put("httpMethod", httpMethod);
        MDC.put("action", action);
        for (int i = 0; i + 1 < additionalKVs.length; i += 2) {
            MDC.put(additionalKVs[i].toString(), additionalKVs[i + 1].toString());
        }
        logger.info("Request initiated.", kv("log_event_type", "API_REQUEST"));
    }

    private void logResponse(HttpStatus status, String message, Object... additionalKVs) {
        for (int i = 0; i + 1 < additionalKVs.length; i += 2) {
            MDC.put(additionalKVs[i].toString(), additionalKVs[i + 1].toString());
        }
        logger.info(message, kv("log_event_type", "API_RESPONSE"), kv("http_status_code", status.value()));
    }

    private void logErrorResponse(HttpStatus status, String message, Throwable e, Object... additionalKVs) {
        for (int i = 0; i + 1 < additionalKVs.length; i += 2) {
            MDC.put(additionalKVs[i].toString(), additionalKVs[i + 1].toString());
        }
        logger.error(message, e, kv("log_event_type", "API_RESPONSE"), kv("http_status_code", status.value()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest,
                                           @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                           HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/register";
        String httpMethod = "POST";
        String action = "register";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", registerRequest.email());

        try {
            authService.register(registerRequest);
            logResponse(HttpStatus.OK, "OTP sent for registration", "email", registerRequest.email());
            return ResponseEntity.ok("Đã gửi otp");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error during registration", e, "email", registerRequest.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Operation(summary = "Xác minh OTP", security = {})
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verify(@RequestBody OtpVerificationRequest requestBody,
                                         @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                         HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/verify-otp";
        String httpMethod = "POST";
        String action = "verifyOtp";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", requestBody.email());

        try {
            authService.verifyOtp(requestBody);
            logResponse(HttpStatus.OK, "OTP verification successful", "email", requestBody.email());
            return ResponseEntity.ok("Xác thực thành công");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.BAD_REQUEST, "OTP verification failed", e, "email", requestBody.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Operation(summary = "Xác minh OTP", security = {})
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody ResendOtpRequest requestBody,
                                            @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                            HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/resend-otp";
        String httpMethod = "POST";
        String action = "resendOtp";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", requestBody.email());

        try {
            authService.resendOtp(requestBody.email());
            logResponse(HttpStatus.OK, "OTP resent", "email", requestBody.email());
            return ResponseEntity.ok("Đã gửi lại mã OTP");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error resending OTP", e, "email", requestBody.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest,
                                              @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                              HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/login";
        String httpMethod = "POST";
        String action = "login";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", loginRequest.email());

        try {
            AuthResponse response = authService.login(loginRequest);
            logResponse(HttpStatus.OK, "User logged in successfully", "email", loginRequest.email(),
                    "userId", response.userId().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logErrorResponse(HttpStatus.UNAUTHORIZED, "Login failed", e, "email", loginRequest.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @PostMapping("refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest,
                                                     @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                                     HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/refresh-token";
        String httpMethod = "POST";
        String action = "refreshToken";

        try {
            var claims = jwtService.extractAllClaims(refreshTokenRequest.refreshToken());
            UUID userId = UUID.fromString(claims.get("userId", String.class));
            var user = userRepo.findById(userId).orElseThrow();

            logRequest(userId.toString(), traceId, clientIp, endpoint, httpMethod, action);

            String newAccessToken = jwtService.generateAccessToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            String role = user.isAdmin() ? "ADMIN" : "USER";
            AuthResponse response = new AuthResponse(newAccessToken, newRefreshToken, user.getId(), role);
            logResponse(HttpStatus.OK, "Token refreshed successfully", "userId", userId.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logErrorResponse(HttpStatus.UNAUTHORIZED, "Refresh token failed", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                                  HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/me";
        String httpMethod = "GET";
        String action = "getCurrentUser";

        UUID userId;
        String role;

        try {
            userId = currentUser.getUserId(request);
            role = currentUser.getUserRole(request);

            logRequest(userId.toString(), traceId, clientIp, endpoint, httpMethod, action);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("role", role);

            logResponse(HttpStatus.OK, "Current user info retrieved", "userId", userId.toString(), "role", role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve current user info", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String authHeader,
                                                 @RequestBody ChangePasswordRequest requestBody,
                                                 @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                                 HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/change-password";
        String httpMethod = "POST";
        String action = "changePassword";

        try {
            if (!requestBody.newPassword().equals(requestBody.confirmNewPassword())) {
                logErrorResponse(HttpStatus.BAD_REQUEST, "New password and confirmation do not match", null);
                return ResponseEntity.badRequest().body("Mật khẩu mới không khớp");
            }

            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractAllClaims(token).getSubject();

            logRequest(email, traceId, clientIp, endpoint, httpMethod, action);

            authService.changePassword(email, requestBody.oldPassword(), requestBody.newPassword());
            logResponse(HttpStatus.OK, "Password changed successfully", "email", email);
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error changing password", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ResendOtpRequest requestBody,
                                                 @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                                 HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/forgot-password";
        String httpMethod = "POST";
        String action = "forgotPassword";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", requestBody.email());

        try {
            authService.forgotPassword(requestBody.email());
            logResponse(HttpStatus.OK, "OTP sent for password reset", "email", requestBody.email());
            return ResponseEntity.ok("Đã gửi mã OTP đặt lại mật khẩu");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error sending OTP for password reset", e, "email", requestBody.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest requestBody,
                                                @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader,
                                                HttpServletRequest request) {
        String traceId = requestHelperService.getOrCreateTraceId(traceIdHeader);
        String clientIp = requestHelperService.getClientIp(request);
        String endpoint = "/api/auth/reset-password";
        String httpMethod = "POST";
        String action = "resetPassword";

        String userId = "anonymous";

        logRequest(userId, traceId, clientIp, endpoint, httpMethod, action, "email", requestBody.email());

        try {
            authService.resetPassword(requestBody.email(), requestBody.otp(), requestBody.newPassword());
            logResponse(HttpStatus.OK, "Password reset successfully", "email", requestBody.email());
            return ResponseEntity.ok("Đặt lại mật khẩu thành công");
        } catch (Exception e) {
            logErrorResponse(HttpStatus.BAD_REQUEST, "Error resetting password", e, "email", requestBody.email());
            throw e;
        } finally {
            MDC.clear();
        }
    }
}
