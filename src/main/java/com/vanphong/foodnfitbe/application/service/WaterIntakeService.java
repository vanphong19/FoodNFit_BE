package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;

import java.util.UUID;

public interface WaterIntakeService {
    void addWaterCups(int cups);
    WaterIntake getWaterCups();
}
