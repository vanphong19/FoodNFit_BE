package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.FirebaseAuthRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.OtpRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.RegisterRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthService {
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final JavaMailSender javaMailSender;
    private final String fromEmail;
    private Map<String, OtpCacheItem> otpCache;

    public AuthService(FirebaseAuthRepository firebaseAuthRepository,
                       JavaMailSender javaMailSender,
                       @Value("${spring.mail.username}") String fromEmail) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.javaMailSender = javaMailSender;
        this.fromEmail = fromEmail;
    }

    @PostConstruct
    public void init() {
        otpCache = new HashMap<>();
    }

    public void sendOtp(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu không khớp");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpCache.put(request.getEmail(), new OtpCacheItem(otp, request, LocalDateTime.now()));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(request.getEmail());
        message.setSubject("Mã OTP xác thực đăng ký tài khoản");
        message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 5 phút.");

        javaMailSender.send(message);
    }

    public void resendOtp(String email) {
        OtpCacheItem existing = otpCache.get(email);
        if (existing == null) {
            throw new RuntimeException("Chưa từng gửi OTP cho email này");
        }

        sendOtp(existing.getRequest());
    }

    public void verifyOtp(OtpRequest otpRequest) {
        OtpCacheItem cached = otpCache.get(otpRequest.getEmail());
        if (cached == null || !cached.getOtp().equals(otpRequest.getOtp())) {
            throw new RuntimeException("OTP không đúng hoặc đã hết hạn");
        }

        if (ChronoUnit.MINUTES.between(cached.getCreatedAt(), LocalDateTime.now()) > 5) {
            otpCache.remove(otpRequest.getEmail());
            throw new RuntimeException("OTP đã hết hạn");
        }

        RegisterRequest data = cached.getRequest();

        // Tạo tài khoản Firebase
        String uid = firebaseAuthRepository.createUser(data.getEmail(), data.getPassword());

        // Nếu uid từ Firebase là chuỗi bình thường (không phải UUID), dùng random UUID thay thế
        Users user = Users.builder()
                .id(UUID.randomUUID()) // hoặc: UUID.fromString(uid) nếu uid là UUID
                .email(data.getEmail())
                .fullname(data.getName())
                .createdDate(LocalDate.now())
                .isActive(true)
                .build();

        //usersJpaRepository.save(user);
        otpCache.remove(otpRequest.getEmail());
    }

    private static class OtpCacheItem {
        private final String otp;
        private final RegisterRequest request;
        private final LocalDateTime createdAt;

        public OtpCacheItem(String otp, RegisterRequest request, LocalDateTime createdAt) {
            this.otp = otp;
            this.request = request;
            this.createdAt = createdAt;
        }

        public String getOtp() {
            return otp;
        }

        public RegisterRequest getRequest() {
            return request;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
