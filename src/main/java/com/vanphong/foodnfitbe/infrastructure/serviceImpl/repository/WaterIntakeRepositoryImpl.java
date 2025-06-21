package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.domain.repository.WaterIntakeRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.WaterIntakeJpaRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WaterIntakeRepositoryImpl implements WaterIntakeRepository {
    private final WaterIntakeJpaRepository waterIntakeJpaRepository;
    @Override
    public Optional<WaterIntake> findByUserIdAndDate(UUID user_id, LocalDate date) {
        return waterIntakeJpaRepository.findByUserIdAndDate(user_id, date);
    }

    @Override
    public WaterIntake save(WaterIntake waterIntake) {
        return waterIntakeJpaRepository.save(waterIntake);
    }

    @Override
    public List<WaterIntake> findLast7DaysWaterIntakeByUserId(UUID user_id, LocalDate startDate) {
        return waterIntakeJpaRepository.findLast7DaysWaterIntakeByUserId(user_id, startDate);
    }
}
