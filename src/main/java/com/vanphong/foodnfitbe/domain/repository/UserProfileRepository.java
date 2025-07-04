package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository {
    Optional<UserProfiles> getLatestByUserID(UUID userId);
    UserProfiles save(UserProfiles userProfiles);
    List<UserProfiles> findAll();
    Double getLatestTDEE(UUID userID);
    List<UserProfiles> findByUserIdAndDateRange(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
}
