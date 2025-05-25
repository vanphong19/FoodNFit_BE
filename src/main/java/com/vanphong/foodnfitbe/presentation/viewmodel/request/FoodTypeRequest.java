package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodTypeRequest {
    private String name;
    private String description;
}
