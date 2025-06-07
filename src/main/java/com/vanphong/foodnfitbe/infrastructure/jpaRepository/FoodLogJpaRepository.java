package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FoodLogJpaRepository extends JpaRepository<FoodLog, Integer> {
    @Query("SELECT f FROM FoodLog f WHERE f.user.id = :userId AND f.date = :date")
    List<FoodLog> findByUserIdAndDate(@Param("userId") UUID userId, @Param("date") LocalDate date);
}
