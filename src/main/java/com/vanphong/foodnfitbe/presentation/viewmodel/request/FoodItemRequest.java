package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemRequest {
    private String nameEn;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private String imageUrl;
    private String servingSizeEn;
    private String recipeEn;
    private Integer foodTypeId;
}
