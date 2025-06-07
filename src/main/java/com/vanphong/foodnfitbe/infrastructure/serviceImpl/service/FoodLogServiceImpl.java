package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.FoodItemService;
import com.vanphong.foodnfitbe.application.service.FoodLogService;
import com.vanphong.foodnfitbe.domain.entity.FoodLog;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.FoodLogDetailRepository;
import com.vanphong.foodnfitbe.domain.repository.FoodLogRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.presentation.mapper.FoodLogMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import com.vanphong.foodnfitbe.utils.MealTypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodLogServiceImpl implements FoodLogService {
    private final FoodLogRepository foodLogRepository;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private final FoodLogMapper foodLogMapper;
    private final FoodLogDetailRepository foodLogDetailRepository;
    @Override
    public FoodLogResponse createFoodLog(FoodLogRequest foodLogRequest) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId).orElseThrow(() -> new RuntimeException("User not found"));
        FoodLog foodLog = FoodLog.builder()
                .user(user)
                .meal(foodLogRequest.getMeal())
                .date(LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                .totalCalories(foodLogRequest.getTotalCalories())
                .totalCarbs(foodLogRequest.getTotalCarbs())
                .totalProtein(foodLogRequest.getTotalProtein())
                .totalFat(foodLogRequest.getTotalFat())
                .build();

        FoodLog saved = foodLogRepository.save(foodLog);
        return foodLogMapper.toResponse(saved);
    }

    @Override
    public FoodLogResponse update(Integer id, FoodLogRequest foodLogRequest) {
        FoodLog foodLog = foodLogRepository.findById(id).orElseThrow(() -> new RuntimeException("FoodLog not found"));

        foodLog.setTotalCalories(foodLogRequest.getTotalCalories());
        foodLog.setTotalCarbs(foodLogRequest.getTotalCarbs());
        foodLog.setTotalProtein(foodLogRequest.getTotalProtein());
        foodLog.setTotalFat(foodLogRequest.getTotalFat());

        FoodLog updated = foodLogRepository.save(foodLog);
        return foodLogMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        FoodLog foodLog = foodLogRepository.findById(id).orElseThrow(() -> new RuntimeException("FoodLog not found"));

        foodLog.getDetails().clear();
        foodLogRepository.delete(foodLog);
    }

    @Override
    public List<FoodLogResponse> getAllFoodLogsByDay(LocalDate day) {
        UUID userId = currentUser.getCurrentUserId();
        List<FoodLog> logs = foodLogRepository.findByUserIdAndDate(userId, day);

        logs.sort(Comparator.comparingInt(log -> MealTypeOrder.getOrder(log.getMeal())));
        return foodLogMapper.toResponseList(logs);
    }

    @Override
    public FoodLogResponse getFoodLogById(Integer id) {
        FoodLog log = foodLogRepository.findById(id).orElseThrow(() -> new RuntimeException("FoodLog not found"));
        return foodLogMapper.toResponse(log);
    }
}
