package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequest {
    private String message;
    private String purpose;
    private String inquiry;
}
