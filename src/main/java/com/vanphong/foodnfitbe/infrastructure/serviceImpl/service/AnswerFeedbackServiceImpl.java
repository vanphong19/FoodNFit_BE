package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.AnswerFeedbackService;
import com.vanphong.foodnfitbe.domain.entity.AnswerFeedback;
import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.AnswerFeedbackRepository;
import com.vanphong.foodnfitbe.domain.repository.FeedbackRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.AnswerFeedbackMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.AnswerFeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AnswerFeedbackResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerFeedbackServiceImpl implements AnswerFeedbackService {
    private final AnswerFeedbackRepository answerFeedbackRepository;
    private final AnswerFeedbackMapper answerFeedbackMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final FeedbackRepository feedbackRepository;
    @Override
    public AnswerFeedbackResponse createAnswerFeedback(AnswerFeedbackRequest answerFeedbackRequest) {
        Optional<Feedback> feedback = feedbackRepository.findById(answerFeedbackRequest.getFeedbackId());
        if(feedback.isEmpty()) {
            throw new NotFoundException("Feedback not found");
        }
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));

        AnswerFeedback answerFeedback = AnswerFeedback.builder().answer(answerFeedbackRequest.getAnswer()).feedback(feedback.get()).user(user).respondedAt(LocalDateTime.now()).build();
        AnswerFeedback savedAnswerFeedback = answerFeedbackRepository.save(answerFeedback);
        return answerFeedbackMapper.toResponse(savedAnswerFeedback);
    }
}
