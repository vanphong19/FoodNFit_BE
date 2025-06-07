package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;

import java.util.List;

public interface StepsTrackingRepository {
    StepsTracking save(StepsTracking stepsTracking);
    List<StepsTracking> getAll();
}
