package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.WaterIntakeService;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.domain.repository.WaterIntakeRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.WaterIntakeMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class WaterIntakeServiceImpl implements WaterIntakeService {
    private final WaterIntakeRepository waterIntakeRepository;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    @Override
    public void addWaterCups(int cups) {
        LocalDate today = LocalDate.now();
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));
        WaterIntake intake = waterIntakeRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() ->{
                    WaterIntake newIntake = new WaterIntake();
                    newIntake.setUser(user);
                    newIntake.setDate(today);
                    return newIntake;});
        intake.setCups(cups);
        intake.setUpdatedAt(LocalDateTime.now());
        waterIntakeRepository.save(intake);
    }

    @Override
    public WaterIntake getWaterCups() {
        LocalDate today = LocalDate.now();
        UUID userId = currentUser.getCurrentUserId();

        return waterIntakeRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> {
                    // Nếu chưa có bản ghi hôm nay thì trả về bản ghi 0 cups (tùy yêu cầu app)
                    WaterIntake intake = new WaterIntake();
                    intake.setUser(userRepository.findUser(userId)
                            .orElseThrow(() -> new NotFoundException("User not found")));
                    intake.setDate(today);
                    intake.setCups(0);
                    intake.setUpdatedAt(LocalDateTime.now());

                    return waterIntakeRepository.save(intake);
                });
    }
}
