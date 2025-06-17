package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

@Data
@Getter
@Setter
@A
@Builder
public class WorkoutExerciseResponse {
     Integer id;

     Integer exerciseId;

     Integer planId;

     Integer sets;
     Integer reps;
     Integer restTimeSecond;
     Double caloriesBurnt;
     Integer minutes;
     Boolean isCompleted;
}
