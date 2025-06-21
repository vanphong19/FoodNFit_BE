package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.domain.repository.FoodLogRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodLogJpaRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodTypeJpaRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.NutritionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FoodLogRepositoryImpl implements FoodLogRepository {
    private final FoodLogJpaRepository foodLogJpaRepository;
    @Override
    public FoodLog save(FoodLog foodLog) {
        return foodLogJpaRepository.save(foodLog);
    }

    @Override
    public Optional<FoodLog> findById(Integer id) {
        return foodLogJpaRepository.findById(id);
    }

    @Override
    public List<FoodLog> findAll() {
        return foodLogJpaRepository.findAll();
    }

    @Override
    public void delete(FoodLog foodLog) {
        foodLogJpaRepository.delete(foodLog);
    }

    @Override
    public List<FoodLog> findByUserIdAndDate(UUID userId, LocalDate date) {
        return foodLogJpaRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public NutritionDto getNutritionStats(UUID userId, LocalDate date) {
        return foodLogJpaRepository.getNutritionStats(userId, date);
    }

    @Override
    public List<FoodLog> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate) {
        return foodLogJpaRepository.findByUser_IdAndDateBetweenOrderByDateAsc(userId, startDate, endDate);
    }

    @Override
    public Optional<FoodLog> findByUserIdAndDateAndMeal(UUID userId, LocalDate date, String meal) {
        return foodLogJpaRepository.findByUser_IdAndDateAndMeal(userId, date, meal);
    }

    @Override
    public List<Object[]> getDailyNutritionSummaryLast7Days(UUID userId, LocalDate startDate) {
        return foodLogJpaRepository.getDailyNutritionSummaryLast7Days(userId, startDate);
    }
}