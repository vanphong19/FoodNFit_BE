package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.FoodTypeService;
import com.vanphong.foodnfitbe.domain.entity.FoodType;
import com.vanphong.foodnfitbe.domain.repository.FoodTypeRepository;
import com.vanphong.foodnfitbe.presentation.mapper.FoodTypeMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodTypeRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodTypeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodTypeServiceImpl implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodTypeMapper foodTypeMapper;

    @Override
    public FoodTypeResponse addFoodType(FoodTypeRequest foodTypeRequest) {
        FoodType foodType = foodTypeMapper.toFoodType(foodTypeRequest);
        FoodType savedFoodType = foodTypeRepository.save(foodType);
        return foodTypeMapper.toFoodTypeResponse(savedFoodType);
    }

    @Override
    public FoodTypeResponse updateFoodType(Integer id, FoodTypeRequest foodTypeRequest) {
        Optional<FoodType> optional = foodTypeRepository.findById(id);
        if (optional.isEmpty()){
            throw new RuntimeException("Food type not found with id: " + id);
        }
        FoodType existing = optional.get();
        existing.setName(foodTypeRequest.getName());
        existing.setDescription(foodTypeRequest.getDescription());
        FoodType updated = foodTypeRepository.save(existing);
        return foodTypeMapper.toFoodTypeResponse(updated);
    }

    @Override
    public FoodTypeResponse deleteFoodType(Integer id) {
        return null;
    }

    @Override
    public List<FoodTypeResponse> getAllFoodTypes() {
        List<FoodType> foodTypes = foodTypeRepository.findAll();
        return foodTypeMapper.toFoodTypeResponses(foodTypes);
    }
}
