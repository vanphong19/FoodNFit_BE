package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface FoodItemJpaRepository extends JpaRepository<FoodItem, Integer> {
    Long countByCreatedDateBetweenAndActiveTrue(LocalDate from, LocalDate to);
}
