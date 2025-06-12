package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ExerciseRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ExerciseMapper {
    public Exercise toEntity(ExerciseRequest request) {
        return Exercise.builder()
                .exerciseName(request.getExerciseName())
                .description(request.getDescription())
                .videoUrl(request.getVideoUrl())
                .imageUrl(request.getImageUrl())
                .difficultyLevel(request.getDifficultyLevel())
                .muscleGroup(request.getMuscleGroup())
                .equipmentRequired(request.getEquipmentRequired())
                .caloriesBurnt(request.getCaloriesBurnt())
                .minutes(request.getMinutes())
                .sets(request.getSets())
                .reps(request.getReps())
                .restTimeSeconds(request.getRestTimeSeconds())
                .note(request.getNote())
                .exerciseType(request.getExerciseType())
                .active(request.getActive())
                .build();
    }

    public ExerciseResponse toResponse(Exercise exercise) {
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getExerciseName())
                .description(exercise.getDescription())
                .videoUrl(exercise.getVideoUrl())
                .imageUrl(exercise.getImageUrl())
                .difficultyLevel(exercise.getDifficultyLevel())
                .muscleGroup(exercise.getMuscleGroup())
                .equipmentRequired(exercise.getEquipmentRequired())
                .caloriesBurnt(exercise.getCaloriesBurnt())
                .minutes(exercise.getMinutes())
                .sets(exercise.getSets())
                .reps(exercise.getReps())
                .restTimeSeconds(exercise.getRestTimeSeconds())
                .note(exercise.getNote())
                .type(exercise.getExerciseType())
                .active(exercise.getActive())
                .build();
    }

    public List<ExerciseResponse> toResponses(List<Exercise> exercises) {
        return exercises.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
