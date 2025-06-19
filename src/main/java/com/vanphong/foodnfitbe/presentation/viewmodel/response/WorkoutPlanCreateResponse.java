package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class WorkoutPlanCreateResponse {
    Integer id;
    UUID userId;
    Integer exerciseCount;
    LocalDate planDate;
    Double totalCaloriesBurnt;
}
