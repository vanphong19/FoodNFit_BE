package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.FcmToken;
import com.vanphong.foodnfitbe.domain.entity.Users;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository {
    List<FcmToken> findByUser(Users user);
    Optional<FcmToken> findByToken(String token);
    FcmToken save(FcmToken token);
}
