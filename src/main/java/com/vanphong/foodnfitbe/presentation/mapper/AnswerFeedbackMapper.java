package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.AnswerFeedback;
import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.AnswerFeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.AnswerFeedbackResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnswerFeedbackMapper {
    public AnswerFeedback toEntity(AnswerFeedbackRequest request) {
        return AnswerFeedback.builder()
                .answer(request.getAnswer())
                .respondedAt(LocalDateTime.now())
                .build();
    }

    public AnswerFeedbackResponse toResponse(AnswerFeedback answerFeedback) {
        return AnswerFeedbackResponse.builder()
                .id(answerFeedback.getId())
                .userId(answerFeedback.getUser().getId())
                .feedbackId(answerFeedback.getFeedback().getId())
                .answer(answerFeedback.getAnswer())
                .respondedAt(answerFeedback.getRespondedAt())
                .build();
    }
}
