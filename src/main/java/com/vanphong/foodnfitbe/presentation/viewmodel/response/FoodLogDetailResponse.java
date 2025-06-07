package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLogDetailResponse {
    private Integer id;
    private Integer foodId;
    private String foodNameEn;
    private String foodNameVi;
    private String servingSize;
    private Double calories;
    private Double carbs;
    private Double protein;
    private Double fat;
}
