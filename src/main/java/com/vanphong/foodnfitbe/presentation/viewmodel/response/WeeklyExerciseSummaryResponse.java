package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class WeeklyExerciseSummaryResponse {
    private Double totalCaloriesBurnt;
    private Integer totalTrainingSessions;
    private Integer totalSteps;
    private Double averageCaloriesPerSession;
    private String bestDay; // e.g., "TUESDAY"
    private List<DailyCaloriesExerciseDto> dailyCalorieChartData;
    private List<FavoriteExerciseDto> favoriteExercises;
}