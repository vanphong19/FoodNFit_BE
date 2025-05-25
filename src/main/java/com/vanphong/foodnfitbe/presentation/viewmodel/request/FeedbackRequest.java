package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    private String message;
    private String purpose;
    private String inquiry;
}
