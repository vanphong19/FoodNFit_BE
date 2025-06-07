package com.vanphong.foodnfitbe.utils;

import com.vanphong.foodnfitbe.application.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUser {
    private final JwtService jwtService;
    public UUID getUserId(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {return null;}

        String token = header.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        return UUID.fromString(claims.get("userId", String.class));
    }

    public String getUserRole(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        return claims.get("role", String.class); // "USER" hoặc "ADMIN"
    }

    // Dùng trong service: không cần truyền request
    public UUID getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        return getUserId(request);
    }

    public String getCurrentUserRole() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        return getUserRole(request);
    }
}
