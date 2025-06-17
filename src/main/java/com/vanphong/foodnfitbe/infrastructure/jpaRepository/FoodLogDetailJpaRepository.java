package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodLogDetailJpaRepository extends JpaRepository<FoodLogDetail, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM FoodLogDetail d WHERE d.log.id = :logId")
    void deleteByLogId(@Param("logId") Integer logId);
}
