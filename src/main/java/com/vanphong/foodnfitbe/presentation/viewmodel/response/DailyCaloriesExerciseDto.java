package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCaloriesExerciseDto {
    LocalDate date;
    Double totalCalories;
    Integer exerciseCount;
}
