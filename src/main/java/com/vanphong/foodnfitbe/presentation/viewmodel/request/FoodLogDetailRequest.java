package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class FoodLogDetailRequest {
    private Integer foodId;
    private String servingSize;
    private Double calories;
    private Double carbs;
    private Double protein;
    private Double fat;
}
