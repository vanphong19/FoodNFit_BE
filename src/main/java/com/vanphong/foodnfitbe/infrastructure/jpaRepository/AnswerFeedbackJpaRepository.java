package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.AnswerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerFeedbackJpaRepository extends JpaRepository<AnswerFeedback, UUID> {
}
