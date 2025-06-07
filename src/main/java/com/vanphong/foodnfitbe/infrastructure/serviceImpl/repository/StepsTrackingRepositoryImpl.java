package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.StepsTrackingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
}
