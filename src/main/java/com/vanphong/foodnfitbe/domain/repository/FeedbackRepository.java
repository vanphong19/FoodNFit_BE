package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Feedback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository {
    Feedback save(Feedback feedback);
    Optional<Feedback> findById(UUID id);
    List<Feedback> findAll();
    Long count();
}
