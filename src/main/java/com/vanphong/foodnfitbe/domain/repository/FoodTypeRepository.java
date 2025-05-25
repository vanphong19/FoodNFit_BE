package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodType;

import java.util.List;
import java.util.Optional;

public interface FoodTypeRepository {
    Optional<FoodType> findById(int id);
    List<FoodType> findAll();
    FoodType save(FoodType foodType);
}
