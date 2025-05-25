package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemResponse {
    private Integer id;
    private String nameEn;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private String imageUrl;
    private String servingSizeEn;
    private String recipeEn;
    private String nameVi;
    private String recipeVi;
    private String servingSizeVi;
    private Integer foodTypeId;
    private Boolean isActive;
}
