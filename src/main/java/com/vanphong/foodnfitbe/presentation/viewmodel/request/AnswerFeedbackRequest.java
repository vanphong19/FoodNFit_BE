package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class AnswerFeedbackRequest {
    private UUID feedbackId;
    private String answer;
    private LocalDateTime respondedAt;
}
