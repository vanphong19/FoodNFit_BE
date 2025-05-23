package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import com.vanphong.foodnfitbe.domain.repository.UserHistoryRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserHistoryJpaRepository;
import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;

@Repository
public class UserHistoryRepositoryImpl implements UserHistoryRepository {
    private final UserHistoryJpaRepository userHistoryJpaRepository;

    public UserHistoryRepositoryImpl(UserHistoryJpaRepository userHistoryJpaRepository) {
        this.userHistoryJpaRepository = userHistoryJpaRepository;
    }

    @Override
    public void save(UserHistory userHistory) {
        userHistoryJpaRepository.save(userHistory);
    }
}
