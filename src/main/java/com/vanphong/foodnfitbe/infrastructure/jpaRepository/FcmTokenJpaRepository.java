package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.FcmToken;
import com.vanphong.foodnfitbe.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FcmTokenJpaRepository extends JpaRepository<FcmToken, UUID> {
    Optional<FcmToken> findByToken(String token);

    List<FcmToken> findByUser(Users user);
}
