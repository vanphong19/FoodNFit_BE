package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseRepository {
    WorkoutExercise save(WorkoutExercise workoutExercise);
    void delete(Integer id);
    Optional<WorkoutExercise> findById(Integer id);
    void deleteByPlanId(Integer planId);
    void saveAll(List<WorkoutExercise> workoutExercises);
}
