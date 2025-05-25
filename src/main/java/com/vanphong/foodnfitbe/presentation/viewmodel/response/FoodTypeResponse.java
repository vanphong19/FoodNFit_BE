package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodTypeResponse {
    private Integer id;
    private String name;
    private String description;
}
