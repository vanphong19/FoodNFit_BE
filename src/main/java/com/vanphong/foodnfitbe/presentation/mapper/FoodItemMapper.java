package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodItemRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodItemResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodItemMapper {
    public FoodItemResponse toResponse(FoodItem foodItem) {
        return FoodItemResponse.builder()
                .id(foodItem.getId())
                .nameEn(foodItem.getNameEn())
                .nameVi(foodItem.getNameVi())
                .calories(foodItem.getCalories())
                .protein(foodItem.getProtein())
                .carbs(foodItem.getCarbs())
                .fat(foodItem.getFat())
                .imageUrl(foodItem.getImageUrl())
                .servingSizeEn(foodItem.getServingSizeEn())
                .servingSizeVi(foodItem.getServingSizeVi())
                .recipeEn(foodItem.getRecipeEn())
                .recipeVi(foodItem.getRecipeVi())
                .foodTypeId(foodItem.getFoodTypeId())
                .active(foodItem.getActive())
                .ingredientsEn(foodItem.getIngredientsEn())
                .createdDate(foodItem.getCreatedDate())
                .build();
    }
    public List<FoodItemResponse> toResponses(List<FoodItem> foodItems) {
        return foodItems.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
