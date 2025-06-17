package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@AllArgsConstructor
@Setter
public class WorkoutPlanResponse {
     Integer id;
     UUID userId;
     Integer exerciseCount;
     LocalDate planDate;
     Double totalCaloriesBurnt;

     List<WorkoutExercise> exercises;
}
