package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class AnswerFeedbackResponse {
    private UUID id;
    private UUID userId;
    private UUID feedbackId;
    private String answer;
    private LocalDateTime respondedAt;
}
