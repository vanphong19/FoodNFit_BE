package com.vanphong.foodnfitbe.presentation.viewmodel.response;

public record UserDailyStatsDto( Double caloriesIn,
                                 Double protein,
                                 Double carbs,
                                 Double fat,
                                 Double caloriesOut,
                                 Double tdee,
                                 Double targetCarbs,
                                 Double targetProtein,
                                 Double targetFat) {
}
