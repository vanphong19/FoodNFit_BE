package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.UserGoal;
import com.vanphong.foodnfitbe.domain.repository.UserGoalRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserGoalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserGoalRepositoryImpl implements UserGoalRepository {
    private final UserGoalJpaRepository userGoalJpaRepository;
    @Override
    public UserGoal save(UserGoal userGoal) {
        return userGoalJpaRepository.save(userGoal);
    }

    @Override
    public Optional<UserGoal> findLatest() {
        return userGoalJpaRepository.findTopByOrderByCreatedAtDesc();
    }
}
