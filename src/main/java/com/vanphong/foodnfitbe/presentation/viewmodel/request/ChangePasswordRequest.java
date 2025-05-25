package com.vanphong.foodnfitbe.presentation.viewmodel.request;

public record ChangePasswordRequest(String oldPassword, String newPassword, String confirmNewPassword) {
}
