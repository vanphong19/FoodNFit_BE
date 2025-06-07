package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FoodLogDetailService;
import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogDetailBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food-log-detail")
@RequiredArgsConstructor
public class FoodLogDetailController {
    private final FoodLogDetailService foodLogDetailService;
    @PostMapping("/batch")
    public ResponseEntity<String> saveBatch(@RequestBody FoodLogDetailBatchRequest request) {
        foodLogDetailService.saveAll(request);
        return ResponseEntity.ok("Saved food log detail successfully.");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        foodLogDetailService.delete(id);
        return ResponseEntity.ok("Deleted food log detail successfully.");
    }
}
