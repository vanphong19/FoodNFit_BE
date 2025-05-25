package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemJpaRepository extends JpaRepository<FoodItem, Integer> {
}
