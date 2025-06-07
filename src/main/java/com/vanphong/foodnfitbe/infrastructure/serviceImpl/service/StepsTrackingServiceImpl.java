package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.StepsTrackingService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.StepsTrackingMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepsTrackingResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StepsTrackingServiceImpl implements StepsTrackingService {
    private final StepsTrackingRepository stepsTrackingRepository;
    private final StepsTrackingMapper stepsTrackingMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    @Override
    public StepsTrackingResponse save(StepsTrackingRequest stepsTrackingRequest) {
        StepsTracking stepsTracking = stepsTrackingMapper.toEntity(stepsTrackingRequest);
        UUID userId = currentUser.getCurrentUserId();
        Users users = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));

        stepsTracking.setUser(users);
        StepsTracking saved = stepsTrackingRepository.save(stepsTracking);
        return stepsTrackingMapper.toResponse(saved);
    }

    @Override
    public List<StepsTrackingResponse> getStepsTrackingList() {
        List<StepsTracking> list = stepsTrackingRepository.getAll();
        return stepsTrackingMapper.toResponseList(list);
    }
}
