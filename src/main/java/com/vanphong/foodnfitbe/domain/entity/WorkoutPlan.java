package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "workout_plan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(name = "exercise_count")
    private Integer exerciseCount;
    @Column(name = "plan_date")
    private LocalDateTime planDate;
}
