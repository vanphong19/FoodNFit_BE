package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.FcmToken;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.FcmTokenRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FcmTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class FcmTokenRepositoryImpl implements FcmTokenRepository {
    private final FcmTokenJpaRepository fcmTokenJpaRepository;
    @Override
    public List<FcmToken> findByUser(Users user) {
        return fcmTokenJpaRepository.findByUser(user);
    }

    @Override
    public Optional<FcmToken> findByToken(String token) {
        return fcmTokenJpaRepository.findByToken(token);
    }

    @Override
    public FcmToken save(FcmToken token) {
        return fcmTokenJpaRepository.save(token);
    }
}
