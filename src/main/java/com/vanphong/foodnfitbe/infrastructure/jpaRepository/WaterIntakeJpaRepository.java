package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface WaterIntakeJpaRepository extends JpaRepository<WaterIntake, Integer> {
    Optional<WaterIntake> findByUserIdAndDate(UUID user_id, LocalDate date);
}
