package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.WaterIntakeService;
import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.presentation.mapper.WaterIntakeMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WaterIntakeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/water")
@RequiredArgsConstructor
public class WaterIntakeController {
    private final WaterIntakeService waterIntakeService;
    private final WaterIntakeMapper waterIntakeMapper;

    @PostMapping("/update")
    public ResponseEntity<String> updateWaterCups(@RequestParam("cups") int cups) {
        waterIntakeService.addWaterCups(cups);
        return ResponseEntity.ok("Cập nhật số ly nước thành công");
    }
    @GetMapping("/today")
        public ResponseEntity<WaterIntakeResponse> getTodayWaterIntake() {
        WaterIntake intake = waterIntakeService.getWaterCups();
        return ResponseEntity.ok(waterIntakeMapper.toResponse(intake));
    }
}
