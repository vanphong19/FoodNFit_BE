package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ExerciseRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    ExerciseResponse createExercise(ExerciseRequest exerciseVModel);
    List<ExerciseResponse> getAllExercises();
    ExerciseResponse updateExercise(Integer id, ExerciseRequest request);
    void deleteExercise(Integer id);
    Optional<ExerciseResponse> getExerciseById(Integer id);
    List<ExerciseResponse> searchExercise(String keyword);
    Long countExercises();
    Long countExerciseCreatedAThisMonth();
}
