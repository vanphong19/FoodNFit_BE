package com.vanphong.foodnfitbe.presentation.viewmodel.request;

public record ResetPasswordRequest(String email, String otp, String newPassword) {
}
