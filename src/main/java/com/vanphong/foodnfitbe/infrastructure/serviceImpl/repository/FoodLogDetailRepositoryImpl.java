package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import com.vanphong.foodnfitbe.domain.repository.FoodLogDetailRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FoodLogDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FoodLogDetailRepositoryImpl implements FoodLogDetailRepository {
    private final FoodLogDetailJpaRepository foodLogDetailJpaRepository;
    @Override
    public FoodLogDetail save(FoodLogDetail foodLogDetail) {
        return foodLogDetailJpaRepository.save(foodLogDetail);
    }

    @Override
    public void delete(Integer id) {
        foodLogDetailJpaRepository.deleteById(id);
    }

    @Override
    public Optional<FoodLogDetail> findById(Integer id) {
        return foodLogDetailJpaRepository.findById(id);
    }
}
