package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodTypeRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodTypeResponse;

import java.util.List;

public interface FoodTypeService {
    FoodTypeResponse addFoodType(FoodTypeRequest foodTypeRequest);
    FoodTypeResponse updateFoodType(Integer id, FoodTypeRequest foodTypeRequest);
    FoodTypeResponse deleteFoodType(Integer id);
    List<FoodTypeResponse> getAllFoodTypes();
}
