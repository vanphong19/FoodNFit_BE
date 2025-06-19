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
    List<WorkoutPlan> findByUser_IdAndPlanDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
    @Query("SELECT e.exerciseName, COUNT(we), SUM(we.caloriesBurnt), e.muscleGroup " +
            "FROM WorkoutExercise we " +
            "JOIN we.exercise e " +
            "JOIN we.plan wp " +
            "WHERE wp.user.id = :userId " +
            "AND wp.planDate BETWEEN :startDate AND :endDate " +
            "AND we.isCompleted = true " +
            "GROUP BY e.id, e.exerciseName, e.muscleGroup " +
            "ORDER BY COUNT(we) DESC")
    List<Object[]> findFavoriteExercisesByUserAndDateRange(@Param("userId") UUID userId,
                                                           @Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);

    Optional<WorkoutPlan> findByUser_IdAndPlanDate(UUID userId, LocalDate date);
}
