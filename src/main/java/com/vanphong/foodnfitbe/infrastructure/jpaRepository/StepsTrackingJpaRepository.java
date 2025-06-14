package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface StepsTrackingJpaRepository extends JpaRepository<StepsTracking, Integer> {
    @Query("SELECT DATE(s.startTime) AS date, SUM(s.stepsCount) AS totalSteps, SUM(s.distance) AS totalDistance " +
            "FROM StepsTracking s " +
            "WHERE s.user.id = :userId AND s.startTime >= :startOfDay AND s.startTime < :startOfNextDay " +
            "GROUP BY DATE(s.startTime)")
    Optional<StepSummary> getTodaySummary(
            @Param("userId") UUID userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay
    );
}

