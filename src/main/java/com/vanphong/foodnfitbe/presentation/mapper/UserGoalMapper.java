package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.UserGoal;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserGoalResponse;
import org.springframework.stereotype.Component;

@Component
public class UserGoalMapper {
    public UserGoalResponse toResponse(UserGoal userGoal) {
        return UserGoalResponse.builder()
                .id(userGoal.getId())
                .targetCalories(userGoal.getTargetCalories())
                .targetProtein(userGoal.getTargetProtein())
                .targetCarbs(userGoal.getTargetCarbs())
                .targetFat(userGoal.getTargetFat())
                .caloriesBreakfast(userGoal.getCaloriesBreakfast())
                .caloriesLunch(userGoal.getCaloriesLunch())
                .caloriesDinner(userGoal.getCaloriesDinner())
                .caloriesSnack(userGoal.getCaloriesSnack())
                .createdAt(userGoal.getCreatedAt())
                .build();
    }
}
