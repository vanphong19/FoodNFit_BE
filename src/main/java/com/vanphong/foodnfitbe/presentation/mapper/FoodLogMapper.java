package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogDetailResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FoodLogMapper {
    public FoodLog toEntity(FoodLogRequest request){
        return FoodLog.builder()
                .totalCalories(request.getTotalCalories())
                .totalFat(request.getTotalFat())
                .totalProtein(request.getTotalProtein())
                .totalCarbs(request.getTotalCarbs())
                .date(LocalDate.now())
                .meal(request.getMeal())
                .build();
    }

    public FoodLogResponse toResponse(FoodLog log) {
        List<FoodLogDetailResponse> detailResponses = Optional.ofNullable(log.getDetails())
                .orElse(Collections.emptyList()) // nếu null thì dùng list rỗng
                .stream()
                .map(detail -> new FoodLogDetailResponse(
                        detail.getId(),
                        detail.getFoodItem().getId(),
                        detail.getFoodItem().getNameEn(),
                        detail.getFoodItem().getNameVi(),
                        detail.getServingSize(),
                        detail.getCalories(),
                        detail.getCarbs(),
                        detail.getProtein(),
                        detail.getFat()
                ))
                .collect(Collectors.toList());

        FoodLogResponse response = new FoodLogResponse();
        response.setId(log.getId());
        response.setMeal(log.getMeal());
        response.setDate(log.getDate());
        response.setTotalCalories(log.getTotalCalories());
        response.setTotalProtein(log.getTotalProtein());
        response.setTotalFat(log.getTotalFat());
        response.setTotalCarbs(log.getTotalCarbs());
        response.setFoodLogDetails(detailResponses);
        return response;
    }

    public List<FoodLogResponse> toResponseList(List<FoodLog> entityList){
        return entityList.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
