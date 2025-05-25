package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.FoodType;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodTypeRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodTypeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodTypeMapper {
    public FoodType toFoodType(FoodTypeRequest request) {
        return FoodType.builder().name(request.getName()).description(request.getDescription()).build();
    }
    public FoodTypeResponse toFoodTypeResponse(FoodType foodType) {
        return FoodTypeResponse.builder().id(foodType.getId()).name(foodType.getName()).description(foodType.getDescription()).build();
    }
    public List<FoodTypeResponse> toFoodTypeResponses(List<FoodType> foodTypes) {
        return foodTypes.stream().map(this::toFoodTypeResponse).collect(Collectors.toList());
    }
}
