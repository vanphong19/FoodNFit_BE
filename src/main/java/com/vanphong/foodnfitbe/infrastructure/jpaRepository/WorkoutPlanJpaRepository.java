package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface WorkoutPlanJpaRepository extends JpaRepository<WorkoutPlan, Integer> {
    @Query("select coalesce(sum(w.exerciseCount), 0) from WorkoutPlan w where w.user.id = :userId and w.planDate = :date")
    Double getCaloriesOut(@Param("userId") UUID userId, @Param("date") LocalDate date);

    @Query("SELECT f FROM WorkoutPlan f WHERE f.user.id = :userId AND f.planDate = :date")
    Optional<WorkoutPlan> findByUserIdAndDate(@Param("userId") UUID userId, @Param("date") LocalDate date);
}
