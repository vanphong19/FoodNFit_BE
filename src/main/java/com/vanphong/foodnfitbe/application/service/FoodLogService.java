package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;

import java.time.LocalDate;
import java.util.List;

public interface FoodLogService {
    FoodLogResponse createFoodLog(FoodLogRequest foodLogRequest);
    FoodLogResponse update(Integer id, FoodLogRequest foodLogRequest);
    void delete(Integer id);
    List<FoodLogResponse> getAllFoodLogsByDay(LocalDate day);
    FoodLogResponse getFoodLogById(Integer id);
}
