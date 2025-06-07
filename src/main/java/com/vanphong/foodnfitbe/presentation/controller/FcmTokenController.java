package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.domain.entity.FcmToken;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.FcmTokenRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FcmTokenRequest;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmTokenController {
    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @PostMapping("/save-token")
    public ResponseEntity<String> saveToken(@RequestBody FcmTokenRequest request) {
        // Lấy thông tin người dùng hiện tại
        UUID userId = currentUser.getCurrentUserId();
        Users users = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));

        // Kiểm tra xem token đã tồn tại trong cơ sở dữ liệu chưa
        Optional<FcmToken> existingToken = fcmTokenRepository.findByToken(request.getToken());
        if (existingToken.isEmpty()) {
            // Nếu chưa tồn tại, lưu token mới vào DB
            FcmToken newFcmToken = FcmToken.builder()
                    .user(users)
                    .token(request.getToken())
                    .build();

            System.out.println("New FcmToken: " + newFcmToken);
            fcmTokenRepository.save(newFcmToken);
            return ResponseEntity.ok("Token saved successfully");
        } else {
            // Nếu token đã tồn tại, trả về thông báo
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token already exists");
        }
    }
}

