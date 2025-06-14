package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // <-- Thêm dòng này
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // <-- Thêm dòng này
    private LocalDateTime endTime;
    private Boolean isWalkingSession;
}
