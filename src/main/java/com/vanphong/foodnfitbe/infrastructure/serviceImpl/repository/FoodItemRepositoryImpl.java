package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FoodItemRepositoryImpl implements FoodItemRepository {
    private final FoodItemJpaRepository foodItemJpaRepository;
    @Override
    public List<FoodItem> findAll() {
        return foodItemJpaRepository.findAll();
    }

    @Override
    public Optional<FoodItem> findById(Integer id) {
        return foodItemJpaRepository.findById(id);
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        return foodItemJpaRepository.save(foodItem);
    }
}
