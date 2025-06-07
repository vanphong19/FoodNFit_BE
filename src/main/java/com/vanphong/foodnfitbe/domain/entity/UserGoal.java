package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_goal")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGoal {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(name = "target_calories")
    private Double targetCalories;
    @Column(name = "target_carbs")
    private Double targetCarbs;
    @Column(name = "target_protein")
    private Double targetProtein;
    @Column(name = "target_fat")
    private Double targetFat;
    @Column(name = "calories_breakfast")
    private Double caloriesBreakfast;
    @Column(name = "calories_lunch")
    private Double caloriesLunch;
    @Column(name = "calories_dinner")
    private Double caloriesDinner;
    @Column(name = "calories_snack")
    private Double caloriesSnack;
    @Column(name = "created_at")
    private LocalDate createdAt;
}
