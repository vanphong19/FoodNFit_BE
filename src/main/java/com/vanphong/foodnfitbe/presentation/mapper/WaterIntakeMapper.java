package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;
import org.springframework.stereotype.Component;

@Component
public class WaterIntakeMapper {
    public WaterIntakeResponse toResponse(WaterIntake waterIntake) {
        return WaterIntakeResponse.builder()
                .id(waterIntake.getId())
                .userId(waterIntake.getUser().getId())
                .date(waterIntake.getDate())
                .cups(waterIntake.getCups())
                .updatedAt(waterIntake.getUpdatedAt())
                .build();
    }
}
