package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.FeedbackService;
import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.FeedbackRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserJpaRepository;
import com.vanphong.foodnfitbe.presentation.mapper.FeedbackMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FeedbackResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final CurrentUser currentUser;
    private final UserJpaRepository userJpaRepository;

    @Override
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userJpaRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("không tìm thấy tài khoản"));
        Feedback savedFeedback = Feedback.builder()
                .user(user)
                .message(feedbackRequest.getMessage())
                .inquiry(feedbackRequest.getInquiry())
                .purpose(feedbackRequest.getPurpose())
                .imageUrl(feedbackRequest.getImageUrl())
                .status(false)
                .submittedAt(LocalDateTime.now())
                .build();
        feedbackRepository.save(savedFeedback);
        return feedbackMapper.toResponse(savedFeedback);
    }

    @Override
    public List<FeedbackResponse> getAllFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbackMapper.toResponses(feedbacks);
    }

    @Override
    public Long countAllFeedback() {
        return feedbackRepository.count();
    }
}
