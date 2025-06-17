package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class WorkoutExerciseRequest {
     Integer exerciseId;
     Integer sets;
     Integer reps;
     Integer restTimeSecond;
     Double caloriesBurnt;
     Integer minutes;
}
