package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.StepsTrackingJpaRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StepsTrackingRepositoryImpl implements StepsTrackingRepository {
    private final StepsTrackingJpaRepository stepsTrackingJpaRepository;
    @Override
    public StepsTracking save(StepsTracking stepsTracking) {
        return stepsTrackingJpaRepository.save(stepsTracking);
    }

    @Override
    public List<StepsTracking> getAll() {
        return stepsTrackingJpaRepository.findAll();
    }

    @Override
    public Optional<StepSummary> getTodaySummary(UUID userId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return stepsTrackingJpaRepository.getTodaySummary(userId, startOfDay, endOfDay);
    }

    @Override
    public List<StepsTracking> findByUserIdAndTimeRange(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        return stepsTrackingJpaRepository.findByUserIdAndTimeRange(userId, startTime, endTime);
    }
}
