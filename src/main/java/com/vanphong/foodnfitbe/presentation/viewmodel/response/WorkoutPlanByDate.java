package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkoutPlanByDate {
    Integer id;
    UUID userId;
    Integer exerciseCount;
    LocalDate planDate;
    Double totalCaloriesBurnt;
    List<WorkoutExerciseResponse> exerciseResponses;
}
