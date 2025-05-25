package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.Feedback;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FeedbackRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FeedbackResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FeedbackMapper {
    public Feedback toEntity(FeedbackRequest request) {
        return Feedback.builder()
                .message(request.getMessage())
                .purpose(request.getPurpose())
                .inquiry(request.getInquiry())
                .submittedAt(LocalDateTime.now())
                .build();
    }

    public FeedbackResponse toResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .userId(feedback.getUser().getId())
                .message(feedback.getMessage())
                .purpose(feedback.getPurpose())
                .inquiry(feedback.getInquiry())
                .submittedAt(feedback.getSubmittedAt())
                .build();
    }
}
