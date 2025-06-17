package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutPlanRepository {
    Double getCaloriesOut(UUID userId, LocalDate date);
    WorkoutPlan save(WorkoutPlan workoutPlan);
    Optional<WorkoutPlan> findByUserAndDate(UUID userId, LocalDate date);
    Optional<WorkoutPlan> findById(Integer id);
    void delete(WorkoutPlan workoutPlan);
}
