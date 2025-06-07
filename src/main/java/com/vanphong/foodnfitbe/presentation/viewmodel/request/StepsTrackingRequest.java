package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StepsTrackingRequest {
    private Integer stepsCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
