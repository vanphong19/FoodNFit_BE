package com.vanphong.foodnfitbe.presentation.viewmodel.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeeklyNutritionResponse {
     Double totalWeeklyCalories;
     List<MealTypeSummaryDto> mealSummaries;
     List<DailyCalorieDataDto> dailyCalorieChartData;
}