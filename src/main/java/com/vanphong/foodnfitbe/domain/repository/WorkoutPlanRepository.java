package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutPlanRepository {
    Double getCaloriesOut(UUID userId, LocalDate date);
    WorkoutPlan save(WorkoutPlan workoutPlan);
    Optional<WorkoutPlan> findByUserAndDate(UUID userId, LocalDate date);
    Optional<WorkoutPlan> findById(Integer id);
    void delete(WorkoutPlan workoutPlan);
    List<WorkoutPlan> findByUserIdAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate);
    List<Object[]> findFavoriteExercisesByUserAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate);

    Optional<WorkoutPlan> findByUserIdAndPlanDate(UUID userId, LocalDate today);
    List<Object[]> getTotalCaloriesBurntByDayLast7Days(UUID userId, LocalDate startDate);
}
