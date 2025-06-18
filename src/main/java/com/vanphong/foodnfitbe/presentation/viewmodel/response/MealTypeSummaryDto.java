package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealTypeSummaryDto {
     String mealType;
     Double totalCalories;
     Double percentage;
}
