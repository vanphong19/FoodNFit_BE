package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileJpaRepository extends JpaRepository<UserProfiles, UUID> {
    Optional<UserProfiles> findTopByUser_IdOrderByCreatedAtDesc(UUID userId);

    @Query("""
    SELECT COALESCE(p.tdee, 0)
    FROM UserProfiles p
    WHERE p.user.id = :userId
    ORDER BY p.createdAt DESC
    LIMIT 1
""")
    Double getLatestTDEE(@Param("userId") UUID userId);
}
