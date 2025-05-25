package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import java.util.UUID;

public record AuthResponse(String accessToken, String refreshToken, UUID userId) {
}
