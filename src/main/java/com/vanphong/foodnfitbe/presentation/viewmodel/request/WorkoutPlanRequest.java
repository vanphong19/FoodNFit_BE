package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@Setter
@Getter
public class WorkoutPlanRequest {
    Integer exerciseCount;
    Double totalCaloriesBurnt;
}
