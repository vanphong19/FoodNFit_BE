package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.NutritionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodLogRepository {
     FoodLog save(FoodLog foodLog);
     Optional<FoodLog> findById(Integer id);
     List<FoodLog> findAll();
     void delete(FoodLog foodLog);
     List<FoodLog> findByUserIdAndDate(UUID userId, LocalDate date);
     NutritionDto getNutritionStats(UUID userId, LocalDate date);
     List<FoodLog> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
     Optional<FoodLog> findByUserIdAndDateAndMeal(UUID userId, LocalDate date, String meal);
}
