package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.StepsTrackingService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
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
}
