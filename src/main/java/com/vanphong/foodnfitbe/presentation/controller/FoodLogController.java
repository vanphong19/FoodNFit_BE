package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FoodLogService;
import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WeeklyNutritionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/food-log")
@RequiredArgsConstructor
public class FoodLogController {
    private final FoodLogService foodLogService;
    @PostMapping("/create")
    public ResponseEntity<FoodLogResponse> create(@RequestBody @Valid FoodLogRequest request) {
        FoodLogResponse response = foodLogService.createFoodLog(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FoodLogResponse> update(@PathVariable Integer id, @RequestBody @Valid FoodLogRequest request) {
        return ResponseEntity.ok(foodLogService.update(id, request));
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        foodLogService.delete(id);
        return ResponseEntity.ok("Food log deleted successfully");
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<FoodLogResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(foodLogService.getFoodLogById(id));
    }
    @GetMapping("/getByDate")
    public ResponseEntity<List<FoodLogResponse>> getByDay(@RequestParam String day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDay = LocalDate.parse(day, formatter);
        return ResponseEntity.ok(foodLogService.getAllFoodLogsByDay(parsedDay));
    }
    @GetMapping("/weekly-summary")
    public ResponseEntity<WeeklyNutritionResponse> getWeeklySummary(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        WeeklyNutritionResponse response = foodLogService.getWeeklySummary(date);
        return ResponseEntity.ok(response);
    }
}
