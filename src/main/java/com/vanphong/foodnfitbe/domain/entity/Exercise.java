package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "description")
    private String description;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @Column(name = "muscle_group")
    private String muscleGroup;

    @Column(name = "equipment_required")
    private String equipmentRequired;

    @Column(name = "calories_burnt")
    private Double caloriesBurnt;

    @Column(name = "minutes")
    private Double minutes;

    @Column(name = "sets")
    private Double sets;

    @Column(name = "reps")
    private Double reps;

    @Column(name = "rest_time_seconds")
    private Integer restTimeSeconds;

    @Column(name = "note")
    private String note;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "is_active")
    private Boolean active;
    @Column(name = "created_date")
    private LocalDate createdDate;
}
