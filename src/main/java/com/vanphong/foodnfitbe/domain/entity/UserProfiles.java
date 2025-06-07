package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfiles {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(name = "height")
    private Float height;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "tdee")
    private Float tdee;
    @Column(name = "meal_goal")
    private String mealGoal;
    @Column(name = "exercise_goal")
    private String exerciseGoal;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
