package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.ExerciseService;
import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ExerciseRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/exercises")
@RestController
public class ExerciseController {
    private final ExerciseService exerciseService;
    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/create")
    public ResponseEntity<ExerciseResponse> create(@RequestBody ExerciseRequest exercise) {
        ExerciseResponse response = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ExerciseResponse>> getAll(
            @RequestParam(defaultValue = "") String search,  // Mặc định là chuỗi rỗng
            @RequestParam(defaultValue = "0") Integer page,  // Mặc định là 0
            @RequestParam(defaultValue = "10") Integer size,  // Mặc định là 10
            @RequestParam(defaultValue = "id") String sortBy,  // Mặc định là "id"
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        SearchCriteria criteria = SearchCriteria.builder()
                .search(search)
                .size(size)
                .page(page)
                .sortDir(sortDir)
                .sortBy(sortBy).build();
        Page<ExerciseResponse> response = exerciseService.getAllExercises(criteria);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExerciseResponse> update(@PathVariable Integer id, @RequestBody ExerciseRequest exercise) {
        ExerciseResponse response = exerciseService.updateExercise(id, exercise);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok("Exercise with id "+ id + " has been removed successfully.");
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable Integer id) {
        Optional<ExerciseResponse> exerciseResponse = exerciseService.getExerciseById(id);
        return exerciseResponse.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("search")
    public ResponseEntity<List<ExerciseResponse>> search(@RequestParam String keyword) {
        List<ExerciseResponse> response = exerciseService.searchExercise(keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(exerciseService.countExercises());
    }

    @GetMapping("count-this-month")
    public ResponseEntity<Long> countThisMonth() {
        return ResponseEntity.ok(exerciseService.countExerciseCreatedAThisMonth());
    }
}
