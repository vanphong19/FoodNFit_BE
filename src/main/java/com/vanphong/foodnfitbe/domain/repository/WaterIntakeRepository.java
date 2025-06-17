package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface WaterIntakeRepository {
    Optional<WaterIntake> findByUserIdAndDate(UUID user_id, LocalDate date);
    WaterIntake save(WaterIntake waterIntake);
}
