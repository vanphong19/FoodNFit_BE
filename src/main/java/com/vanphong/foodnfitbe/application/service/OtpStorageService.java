package com.vanphong.foodnfitbe.application.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpStorageService {
    // Nếu bạn dự định triển khai thật, hãy cân nhắc chuyển từ ConcurrentHashMap sang Redis hoặc Guava Cache để đảm bảo OTP không mất khi restart server
    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> expiryMap = new ConcurrentHashMap<>();

    public static final int OTP_EXPIRE_MINUTES = 5;

    public void storeOtp(String email, String otp, int minutes) {
        otpMap.put(email, otp);
        expiryMap.put(email, LocalDateTime.now().plusMinutes(minutes));
    }

    public String getOtp(String email) {
        return otpMap.get(email);
    }

    public LocalDateTime getExpiry(String email) {
        return expiryMap.get(email);
    }

    public boolean isExpired(String email) {
        return expiryMap.get(email) == null || expiryMap.get(email).isBefore(LocalDateTime.now());
    }

    public void clearOtp(String email) {
        otpMap.remove(email);
        expiryMap.remove(email);
        // ✅ Đảm bảo dọn dẹp sau khi xác minh thành công để tránh OTP reuse
    }
}
