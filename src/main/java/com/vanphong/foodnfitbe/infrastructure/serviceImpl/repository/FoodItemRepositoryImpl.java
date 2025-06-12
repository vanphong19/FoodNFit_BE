package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FoodItemRepositoryImpl implements FoodItemRepository {
    private final FoodItemJpaRepository foodItemJpaRepository;
    @Override
    public Page<FoodItem> findAll(Specification<FoodItem> specification, PageRequest pageRequest) {
        return foodItemJpaRepository.findAll(specification, pageRequest);
    }

    @Override
    public Optional<FoodItem> findById(Integer id) {
        return foodItemJpaRepository.findById(id);
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        return foodItemJpaRepository.save(foodItem);
    }

    @Override
    public Boolean existsById(Integer id) {
        return foodItemJpaRepository.existsById(id);
    }

    @Override
    public Long count() {
        return foodItemJpaRepository.count();
    }

    @Override
    public Long countFoodCreatedThisMonth(LocalDate from, LocalDate to) {
        return foodItemJpaRepository.countByCreatedDateBetweenAndActiveTrue(from, to);
    }
}
