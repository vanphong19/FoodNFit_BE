package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExerciseRequest {
    private String exerciseName;
    private String description;
    private String videoUrl;
    private String imageUrl;
    private String difficultyLevel;
    private String muscleGroup;
    private String equipmentRequired;
    private Double caloriesBurnt;
    private Double minutes;
    private Double sets;
    private Double reps;
    private Integer restTimeSeconds;
    private String note;
    private String exerciseType;
    private Boolean isActive;
}
