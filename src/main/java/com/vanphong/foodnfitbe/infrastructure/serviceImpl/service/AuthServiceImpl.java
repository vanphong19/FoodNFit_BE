package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.AuthService;
import com.vanphong.foodnfitbe.application.service.JwtService;
import com.vanphong.foodnfitbe.application.service.MailService;
import com.vanphong.foodnfitbe.application.service.OtpStorageService;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.JpaUserRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.LoginRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.OtpVerificationRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.RegisterRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AuthResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JpaUserRepository repo;
    private final JwtService jwtService;
    private final MailService mailService;
    private final OtpStorageService otpStorageService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        var user = repo.findByEmail(loginRequest.email()).orElseThrow();
        if(!user.isVerified()) throw new IllegalStateException("Tài khoản chưa xác minh email");
        if (!user.isActive()) throw new IllegalStateException("Tài khoản ngừng hoạt động");
        if (!passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())) throw new BadCredentialsException("Sai mật khẩu");

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, user.getId());
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if(!registerRequest.password().equals(registerRequest.confirmPassword()))
            throw new IllegalArgumentException("Mật khẩu không khớp");

        if(repo.findByEmail(registerRequest.email()).isPresent())
            throw new IllegalArgumentException("Email đã tồn tại");

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        var user = Users.builder()
                .email(registerRequest.email())
                .passwordHash(passwordEncoder.encode(registerRequest.password()))
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .gender(false)           // hoặc true tùy logic
                .isActive(true)
                .isVerified(false)
                .isBlock(false)
                .build();
        repo.save(user);
        otpStorageService.storeOtp(user.getEmail(), otp, OtpStorageService.OTP_EXPIRE_MINUTES);
        mailService.sendOtp(registerRequest.email(), otp);
    }

    @Override
    public void verifyOtp(OtpVerificationRequest otpRequest) {
        var user = repo.findByEmail(otpRequest.email()).orElseThrow();

        String storedOtp = otpStorageService.getOtp(user.getEmail());
        if (storedOtp == null) throw new IllegalArgumentException("Chưa có OTP hoặc tài khoản đã xác minh");
        if (!storedOtp.equals(otpRequest.otp())) throw new IllegalArgumentException("OTP không chính xác");
        if (otpStorageService.isExpired(user.getEmail())) throw new IllegalArgumentException("OTP đã hết hạn");

        user.setVerified(true);
        repo.save(user);
        otpStorageService.clearOtp(user.getEmail()); // ✅ Dọn dẹp OTP sau xác minh thành công
    }

    @Override
    public void resendOtp(String email) {
        var user = repo.findByEmail(email).orElseThrow();
        if (user.isVerified()) throw new IllegalStateException("Tài khoản đã xác minh");

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStorageService.storeOtp(email, otp, OtpStorageService.OTP_EXPIRE_MINUTES);
        user.setUpdatedDate(LocalDate.now());
        repo.save(user);
        mailService.sendOtp(email, otp);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        var user = repo.findByEmail(email).orElseThrow();
        if(!passwordEncoder.matches(oldPassword, user.getPasswordHash())){
            throw new BadCredentialsException("Mật khẩu cũ không đúng");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedDate(LocalDate.now());
        repo.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStorageService.storeOtp(email, otp, OtpStorageService.OTP_EXPIRE_MINUTES);
        mailService.sendOtp(email, otp);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        var user = repo.findByEmail(email).orElseThrow();
        String storedOtp = otpStorageService.getOtp(email);
        if(storedOtp == null || !storedOtp.equals(otp)){
            throw new IllegalArgumentException("Otp không đúng hoặc đã hết hạn");
        }

        if (otpStorageService.isExpired(email)) throw new IllegalArgumentException("Otp hết hạn");
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedDate(LocalDate.now());
        repo.save(user);
        otpStorageService.clearOtp(email);
    }
}
