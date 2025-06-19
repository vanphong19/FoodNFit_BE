package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutExerciseResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanByDate;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanCreateResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

    public WorkoutPlanByDate toWorkoutPlanByDate(WorkoutPlan plan) {
        List<WorkoutExerciseResponse> exerciseResponses = plan.getExercises().stream()
                .map(exercise -> WorkoutExerciseResponse.builder()
                        .id(exercise.getId())
                        .exerciseName(exercise.getExercise().getExerciseName()) // cần get từ bảng Exercise
                        .planId(plan.getId())
                        .sets(exercise.getSets())
                        .reps(exercise.getReps())
                        .restTimeSecond(exercise.getRestTimeSecond())
                        .caloriesBurnt(exercise.getCaloriesBurnt())
                        .minutes(exercise.getMinutes())
                        .isCompleted(exercise.getIsCompleted())
                        .build()
                ).toList();

        return WorkoutPlanByDate.builder()
                .id(plan.getId())
                .userId(plan.getUser().getId())
                .exerciseCount(plan.getExerciseCount())
                .planDate(plan.getPlanDate())
                .totalCaloriesBurnt(plan.getTotalCaloriesBurnt())
                .exerciseResponses(exerciseResponses)
                .build();
    }

}
