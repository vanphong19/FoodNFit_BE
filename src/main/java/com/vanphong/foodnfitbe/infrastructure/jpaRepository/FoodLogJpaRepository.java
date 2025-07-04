package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.NutritionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodLogJpaRepository extends JpaRepository<FoodLog, Integer> {
    @Query("SELECT f FROM FoodLog f WHERE f.user.id = :userId AND f.date = :date")
    List<FoodLog> findByUserIdAndDate(@Param("userId") UUID userId, @Param("date") LocalDate date);

    @Query("""
    SELECT new com.vanphong.foodnfitbe.presentation.viewmodel.response.NutritionDto(
        COALESCE(SUM(f.totalCalories), 0),
        COALESCE(SUM(f.totalProtein), 0),
        COALESCE(SUM(f.totalCarbs), 0),
        COALESCE(SUM(f.totalFat), 0)
    )
    FROM FoodLog f
    WHERE f.user.id = :userId AND f.date = :date
""")
    NutritionDto getNutritionStats(@Param("userId") UUID userId, @Param("date") LocalDate date);

    List<FoodLog> findByUser_IdAndDateBetweenOrderByDateAsc(UUID user_id, LocalDate date, LocalDate date2);

    Optional<FoodLog> findByUser_IdAndDateAndMeal(UUID userId, LocalDate date, String meal);
    @Query("""
        SELECT f.date, 
               SUM(f.totalCalories), 
               SUM(f.totalProtein), 
               SUM(f.totalCarbs), 
               SUM(f.totalFat)
        FROM FoodLog f
        WHERE f.user.id = :userId AND f.date >= :startDate
        GROUP BY f.date
        ORDER BY f.date
    """)
    List<Object[]> getDailyNutritionSummaryLast7Days(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate
    );

}
