package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;

import java.util.Optional;

public interface FoodLogDetailRepository {
    FoodLogDetail save(FoodLogDetail foodLogDetail);
    void delete(Integer id);
    Optional<FoodLogDetail> findById(Integer id);
    void deleteByLogId(Integer logId);
}
