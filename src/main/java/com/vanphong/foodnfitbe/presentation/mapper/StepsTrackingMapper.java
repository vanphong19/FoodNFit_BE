package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepsTrackingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StepsTrackingMapper {
    public StepsTracking toEntity(StepsTrackingRequest request){
        return StepsTracking.builder()
                .stepsCount(request.getStepsCount())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isWalkingSession(request.getIsWalkingSession())
                .build();
    }

    public StepsTrackingResponse toResponse(StepsTracking entity){
        return StepsTrackingResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .stepsCount(entity.getStepsCount())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .isWalkingSession(entity.getIsWalkingSession())
                .build();
    }

    public List<StepsTrackingResponse> toResponseList(List<StepsTracking> entities){
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
