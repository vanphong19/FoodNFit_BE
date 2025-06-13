package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodItemRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodItemResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FoodItemService {
    FoodItemResponse addFoodItem(FoodItemRequest request);
    FoodItemResponse updateFoodItem(Integer id, FoodItemRequest request);
    void deleteFoodItem(Integer id);
    Page<FoodItemResponse> getAllFoodItems(SearchCriteria criteria);
    Optional<FoodItemResponse> getFoodItemById(Integer id);
    Long countFoodCreatedThisMonth();
    FoodItemResponse getById(Integer id);
}
