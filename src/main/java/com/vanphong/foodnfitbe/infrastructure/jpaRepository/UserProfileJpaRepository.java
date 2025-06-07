package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileJpaRepository extends JpaRepository<UserProfiles, UUID> {
    Optional<UserProfiles> findTopByUserIdOrderByCreatedAtDesc(UUID userId);
}
