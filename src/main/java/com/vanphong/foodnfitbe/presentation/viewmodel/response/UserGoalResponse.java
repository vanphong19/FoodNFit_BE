package com.vanphong.foodnfitbe.presentation.viewmodel.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGoalResponse {
    private UUID id;
    private Double targetCalories;
    private Double targetCarbs;
    private Double targetProtein;
    private Double targetFat;
    private Double caloriesBreakfast;
    private Double caloriesLunch;
    private Double caloriesDinner;
    private Double caloriesSnack;
    private LocalDate createdAt;
}
