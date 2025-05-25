package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository {
    List<FoodItem> findAll();
    Optional<FoodItem> findById(Integer id);
    FoodItem save(FoodItem foodItem);
}
