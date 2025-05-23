package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserHistoryJpaRepository extends JpaRepository<UserHistory, UUID> {
}
