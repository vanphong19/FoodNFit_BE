package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.HourlyStepSummary;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepsTrackingResponse;

import java.util.List;

public interface StepsTrackingService {
    StepsTrackingResponse save(StepsTrackingRequest stepsTrackingRequest);
    List<StepsTrackingResponse> getStepsTrackingList();
    StepSummary countStepsAndDistance();
    List<HourlyStepSummary> getHourlyStepsForToday();
}
