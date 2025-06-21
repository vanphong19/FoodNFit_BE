package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WaterIntakeJpaRepository extends JpaRepository<WaterIntake, Integer> {
    Optional<WaterIntake> findByUserIdAndDate(UUID user_id, LocalDate date);
    @Query("SELECT w FROM WaterIntake w WHERE w.user.id = :userId AND w.date >= :startDate")
    List<WaterIntake> findLast7DaysWaterIntakeByUserId(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate
    );
}
