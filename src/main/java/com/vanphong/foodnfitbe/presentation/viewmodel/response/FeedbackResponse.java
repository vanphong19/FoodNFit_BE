package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private UUID id;
    private UUID userId;
    private String message;
    private String purpose;
    private String inquiry;
    private LocalDateTime submittedAt;
}
