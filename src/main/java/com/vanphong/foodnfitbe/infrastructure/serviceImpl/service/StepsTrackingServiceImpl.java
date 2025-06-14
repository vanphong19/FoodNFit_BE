package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.StepsTrackingService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.StepsTrackingMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepSummary;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.StepsTrackingResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public StepSummary countStepsAndDistance() {
        UUID userId = currentUser.getCurrentUserId();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();

        Optional<StepSummary> summaryOpt = stepsTrackingRepository.getTodaySummary(userId, startOfDay, startOfNextDay);

        if (summaryOpt.isPresent()) {
            StepSummary summary = summaryOpt.get();
            log.info("📊 Hôm nay ({}) có: {} bước, {} mét", summary.getDate(), summary.getTotalSteps(), summary.getTotalDistance());
            return summary;
        } else {
            log.info("📭 Không có dữ liệu bước chân hôm nay.");
            // Nếu muốn tránh trả về null, bạn có thể tạo 1 StepSummary mặc định như dưới hoặc dùng Optional trả về lên trên
            return new StepSummary() {
                @Override
                public LocalDate getDate() { return today; }
                @Override
                public Long getTotalSteps() { return 0L; }
                @Override
                public Float getTotalDistance() { return 0f; }
            };
        }
    }
}
