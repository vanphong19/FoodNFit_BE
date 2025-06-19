package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.domain.repository.WorkoutPlanRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.WorkoutPlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class WorkoutPlanRepositoryImpl implements WorkoutPlanRepository {
    private final WorkoutPlanJpaRepository workoutPlanJpaRepository;
    @Override
    public Double getCaloriesOut(UUID userId, LocalDate date) {
        return workoutPlanJpaRepository.getCaloriesOut(userId, date);
    }

    @Override
    public WorkoutPlan save(WorkoutPlan workoutPlan) {
        return workoutPlanJpaRepository.save(workoutPlan);
    }

    @Override
    public Optional<WorkoutPlan> findByUserAndDate(UUID userId, LocalDate date) {
        return workoutPlanJpaRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public Optional<WorkoutPlan> findById(Integer id) {
        return workoutPlanJpaRepository.findById(id);
    }

    @Override
    public void delete(WorkoutPlan workoutPlan) {
        workoutPlanJpaRepository.delete(workoutPlan);
    }

    @Override
    public List<WorkoutPlan> findByUserIdAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return workoutPlanJpaRepository.findByUser_IdAndPlanDateBetween(userId, startDate, endDate);
    }

    @Override
    public List<Object[]> findFavoriteExercisesByUserAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return workoutPlanJpaRepository.findFavoriteExercisesByUserAndDateRange(userId, startDate, endDate);
    }

    @Override
    public Optional<WorkoutPlan> findByUserIdAndPlanDate(UUID userId, LocalDate today) {
        return workoutPlanJpaRepository.findByUser_IdAndPlanDate(userId, today);
    }
}
