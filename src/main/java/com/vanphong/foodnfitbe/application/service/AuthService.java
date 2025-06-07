package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.LoginRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.OtpVerificationRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.RegisterRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);
    void verifyOtp(OtpVerificationRequest otpRequest);
    void resendOtp(String email);
    void changePassword(String email, String oldPassword, String newPassword);
    void forgotPassword(String email);
    void resetPassword(String email, String otp, String newPassword);
}