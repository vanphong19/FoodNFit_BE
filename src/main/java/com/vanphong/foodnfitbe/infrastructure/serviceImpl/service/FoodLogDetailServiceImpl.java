package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.FoodLogDetailService;
import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.domain.repository.FoodLogDetailRepository;
import com.vanphong.foodnfitbe.domain.repository.FoodLogRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogDetailBatchRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogDetailRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodLogDetailServiceImpl implements FoodLogDetailService {
    private final FoodLogDetailRepository foodLogDetailRepository;
    private final FoodLogRepository foodLogRepository;
    private final FoodItemRepository foodItemRepository;
    @Override
    public void saveAll(FoodLogDetailBatchRequest request) {
        FoodLog log = foodLogRepository.findById(request.getLogId()).orElseThrow(() -> new RuntimeException("FoodLog not found"));

        for(FoodLogDetailRequest dto: request.getDetails()){
            FoodItem foodItem = foodItemRepository.findById(dto.getFoodId()).orElseThrow(() -> new RuntimeException("FoodItem not found"));

            FoodLogDetail foodLogDetail = FoodLogDetail.builder()
                    .log(log)
                    .foodItem(foodItem)
                    .servingSize(dto.getServingSize())
                    .calories(dto.getCalories())
                    .fat(dto.getFat())
                    .carbs(dto.getCarbs())
                    .protein(dto.getProtein())
                    .build();

            foodLogDetailRepository.save(foodLogDetail);
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<FoodLogDetail> detail = foodLogDetailRepository.findById(id);
        if(detail.isEmpty())
            throw new RuntimeException("FoodLog detail not found");

        foodLogDetailRepository.delete(id);
    }
}
