package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodLogDetailJpaRepository extends JpaRepository<FoodLogDetail, Integer> {
}
