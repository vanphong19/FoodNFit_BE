package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.StepsTrackingService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.StepsTrackingMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.StepsTrackingRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.HourlyStepSummary;
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
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<HourlyStepSummary> getHourlyStepsForToday() {
        // Lấy thời gian bắt đầu và kết thúc của ngày hiện tại
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zoneId);
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        UUID userId = currentUser.getCurrentUserId();
        List<StepsTracking> todayRecords = stepsTrackingRepository
                .findByUserIdAndTimeRange(userId, startOfDay, endOfDay);

        // Nhóm theo giờ và tính tổng
        Map<Integer, List<StepsTracking>> recordsByHour = todayRecords.stream()
                .collect(Collectors.groupingBy(this::getHourFromRecord));

        // Tạo danh sách kết quả cho tất cả 24 giờ
        List<HourlyStepSummary> result = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            List<StepsTracking> hourRecords = recordsByHour.getOrDefault(hour, List.of());

            long totalSteps = hourRecords.stream()
                    .mapToLong(record -> record.getStepsCount() != null ? record.getStepsCount() : 0)
                    .sum();

            float totalDistance = (float) hourRecords.stream()
                    .mapToDouble(record -> record.getDistance() != null ? record.getDistance() : 0.0)
                    .sum();

            int sessionCount = hourRecords.size();

            result.add(new HourlyStepSummary(hour, totalSteps, totalDistance, sessionCount));
        }

        return result;
    }

    private int getHourFromRecord(StepsTracking record) {
        // Ưu tiên startTime, nếu null thì dùng endTime
        LocalDateTime timeToUse = record.getStartTime() != null
                ? record.getStartTime()
                : record.getEndTime();
        return timeToUse != null ? timeToUse.getHour() : 0;
    }
}
