package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutExerciseBatchRequest;

public interface WorkoutExerciseService {
    void saveAll(WorkoutExerciseBatchRequest request);
    void delete(Integer id);
    WorkoutExercise changeStatus(Integer id);
}
