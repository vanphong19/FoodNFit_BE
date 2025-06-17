package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class HourlyStepSummary {
    private int hour;
    private long totalSteps;
    private float totalDistance;
    private int sessionCount;

    @Override
    public String toString() {
        return String.format("Giờ %02d:00 - %d bước, %.2f km, %d phiên",
                hour, totalSteps, totalDistance, sessionCount);
    }
}
