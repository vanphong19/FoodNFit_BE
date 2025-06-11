package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_exercise")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private WorkoutPlan plan;

    @Column(name = "sets")
    private Integer sets;
    @Column(name = "reps")
    private Integer reps;
    @Column(name = "rest_time_second")
    private Integer restTimeSecond;
    @Column(name = "calories_burnt")
    private Double caloriesBurnt;
    @Column(name = "minutes")
    private Integer minutes;
    @Column(name = "is_completed")
    private Boolean isCompleted;
}
