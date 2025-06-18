package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteExerciseDto {
    private String exerciseName;
    private Integer totalSessions;
    private Double totalCalories;
    private String muscleGroup;
}