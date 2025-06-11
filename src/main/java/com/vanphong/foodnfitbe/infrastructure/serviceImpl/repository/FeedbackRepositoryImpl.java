package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.domain.repository.FeedbackRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.FeedbackJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackRepository {
    private final FeedbackJpaRepository feedbackJpaRepository;
    @Override
    public Feedback save(Feedback feedback) {
        return feedbackJpaRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(UUID id) {
        return feedbackJpaRepository.findById(id);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackJpaRepository.findAll();
    }

    @Override
    public Long count() {
        return feedbackJpaRepository.count();
    }
}
