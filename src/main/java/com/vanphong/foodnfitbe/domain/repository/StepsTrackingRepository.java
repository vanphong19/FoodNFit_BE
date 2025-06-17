package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StepsTrackingRepository {
    StepsTracking save(StepsTracking stepsTracking);
    List<StepsTracking> getAll();
    Optional<StepSummary> getTodaySummary(UUID userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<StepsTracking> findByUserIdAndTimeRange(UUID userId, LocalDateTime startTime, LocalDateTime endTime);
}
