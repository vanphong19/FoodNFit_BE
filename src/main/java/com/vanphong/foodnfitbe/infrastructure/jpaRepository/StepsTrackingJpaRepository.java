package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepsTrackingJpaRepository extends JpaRepository<StepsTracking, Integer> {
}
