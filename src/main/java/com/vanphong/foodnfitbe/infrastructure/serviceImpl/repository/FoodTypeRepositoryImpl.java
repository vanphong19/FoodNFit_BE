package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodType;
import com.vanphong.foodnfitbe.domain.repository.FoodTypeRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FoodTypeRepositoryImpl implements FoodTypeRepository {
    private final FoodTypeJpaRepository foodTypeJpaRepository;

    @Override
    public Optional<FoodType> findById(int id) {
        return foodTypeJpaRepository.findById(id);
    }

    @Override
    public List<FoodType> findAll() {
        return foodTypeJpaRepository.findAll();
    }

    @Override
    public FoodType save(FoodType foodType) {
        return foodTypeJpaRepository.save(foodType);
    }
}
