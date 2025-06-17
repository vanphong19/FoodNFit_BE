package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.AnswerFeedback;
import com.vanphong.foodnfitbe.domain.repository.AnswerFeedbackRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.AnswerFeedbackJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnswerFeedbackRepositoryImpl implements AnswerFeedbackRepository {
    private final AnswerFeedbackJpaRepository answerFeedbackJpaRepository;
    @Override
    public AnswerFeedback save(AnswerFeedback answerFeedback) {
        return answerFeedbackJpaRepository.save(answerFeedback);
    }
}
