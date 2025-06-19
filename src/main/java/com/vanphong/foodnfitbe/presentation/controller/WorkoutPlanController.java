package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.WorkoutPlanService;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WeeklyExerciseSummaryResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanByDate;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanCreateResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/workout-plan")
@RequiredArgsConstructor
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;
    @PostMapping("/create")
    public ResponseEntity<WorkoutPlanCreateResponse> createWorkoutPlan(@RequestBody WorkoutPlanRequest workoutPlanRequest) {
        WorkoutPlanCreateResponse response = workoutPlanService.create(workoutPlanRequest);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<WorkoutPlanResponse> updateWorkoutPlan(@PathVariable Integer id, @RequestBody WorkoutPlanRequest workoutPlanRequest) {
        WorkoutPlanResponse response = workoutPlanService.update(id, workoutPlanRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeWorkoutPlan(@PathVariable Integer id) {
        workoutPlanService.delete(id);
        return ResponseEntity.ok("Deleted workout plan successfully.");
    }
    @GetMapping("/getByDate")
    public ResponseEntity<WorkoutPlanByDate> getWorkoutPlanByDate(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDay = LocalDate.parse(date, formatter);
        return ResponseEntity.ok(workoutPlanService.getByDate(parsedDay));
    }

    @GetMapping("/weekly-summary")
    public ResponseEntity<WeeklyExerciseSummaryResponse> getWeeklySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        WeeklyExerciseSummaryResponse summary = workoutPlanService.getWeeklyExerciseSummary(date);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/weekly-summary/current")
    public ResponseEntity<WeeklyExerciseSummaryResponse> getCurrentWeeklySummary() {
        LocalDate currentDate = LocalDate.now();
        WeeklyExerciseSummaryResponse summary = workoutPlanService.getWeeklyExerciseSummary(currentDate);
        return ResponseEntity.ok(summary);
    }
}
