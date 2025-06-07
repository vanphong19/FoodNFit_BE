package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserGoalJpaRepository extends JpaRepository<UserGoal, UUID> {
    Optional<UserGoal> findTopByOrderByCreatedAtDesc();
}
