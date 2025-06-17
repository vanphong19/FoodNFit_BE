package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.domain.repository.UserProfileRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Override
    public Optional<UserProfiles> getLatestByUserID(UUID userId) {
        return userProfileJpaRepository.findTopByUser_IdOrderByCreatedAtDesc(userId);
    }

    @Override
    public UserProfiles save(UserProfiles userProfiles) {
        return userProfileJpaRepository.save(userProfiles);
    }

    @Override
    public List<UserProfiles> findAll() {
        return userProfileJpaRepository.findAll();
    }

    @Override
    public Double getLatestTDEE(UUID userID) {
        return userProfileJpaRepository.getLatestTDEE(userID);
    }
}
