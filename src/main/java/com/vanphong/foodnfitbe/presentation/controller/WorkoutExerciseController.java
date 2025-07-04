package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.WorkoutExerciseService;
import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutExerciseBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-exercise")
@RequiredArgsConstructor
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;
    @PostMapping("/batch")
    public ResponseEntity<Void> saveAll(@RequestBody WorkoutExerciseBatchRequest request) {
        workoutExerciseService.saveAll(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        workoutExerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<String> complete(@PathVariable Integer id) {
        workoutExerciseService.changeStatus(id);
        return ResponseEntity.ok("updated successfully");
    }
}
