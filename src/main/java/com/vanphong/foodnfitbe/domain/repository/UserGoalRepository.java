package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.UserGoal;

import java.util.Optional;

public interface UserGoalRepository {
    UserGoal save(UserGoal userGoal);
    Optional<UserGoal> findLatest();
}
