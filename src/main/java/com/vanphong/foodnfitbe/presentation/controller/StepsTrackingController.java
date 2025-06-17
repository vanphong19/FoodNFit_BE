package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.StepsTrackingService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.HourlyStepSummary;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepsTrackingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
@RequiredArgsConstructor
public class StepsTrackingController {
    private final StepsTrackingService stepsTrackingService;
    @PostMapping("/create")
    public ResponseEntity<StepsTrackingResponse> create(@RequestBody StepsTrackingRequest request) {
        StepsTrackingResponse response = stepsTrackingService.save(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<StepsTrackingResponse>> getAll() {
        List<StepsTrackingResponse> list = stepsTrackingService.getStepsTrackingList();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/today-summary")
    public ResponseEntity<StepSummary> getTodaySummary() {
        StepSummary summary = stepsTrackingService.countStepsAndDistance();
        if (summary == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(summary);
    }
    @GetMapping("hourly")
    public ResponseEntity<List<HourlyStepSummary>> getHourlySummary() {
        List<HourlyStepSummary> summaries = stepsTrackingService.getHourlyStepsForToday();
        if (summaries == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(summaries);
    }
}
