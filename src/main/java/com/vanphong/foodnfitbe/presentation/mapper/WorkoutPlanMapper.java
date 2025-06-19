package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanCreateResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WorkoutPlanMapper {
    public WorkoutPlanResponse toResponse(WorkoutPlan workoutPlan) {
        return WorkoutPlanResponse.builder()
                .id(workoutPlan.getId())
                .userId(workoutPlan.getUser().getId())
                .totalCaloriesBurnt(workoutPlan.getTotalCaloriesBurnt())
                .exerciseCount(workoutPlan.getExerciseCount())
                .planDate(workoutPlan.getPlanDate())
                .exercises(workoutPlan.getExercises())
                .build();
    }

    public WorkoutPlanCreateResponse toCreateResponse(WorkoutPlan workoutPlan) {
        return WorkoutPlanCreateResponse.builder()
                .id(workoutPlan.getId())
                .userId(workoutPlan.getUser().getId())
                .totalCaloriesBurnt(workoutPlan.getTotalCaloriesBurnt())
                .exerciseCount(workoutPlan.getExerciseCount())
                .planDate(workoutPlan.getPlanDate())
                .build();
    }
}
