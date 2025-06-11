package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseResponse {
    private Integer id;
    private String name;
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
    private String type;
    private Boolean active;
}
