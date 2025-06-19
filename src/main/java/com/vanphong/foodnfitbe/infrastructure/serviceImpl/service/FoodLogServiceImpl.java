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
import com.vanphong.foodnfitbe.presentation.viewmodel.response.DailyCalorieDataDto;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.MealTypeSummaryDto;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WeeklyNutritionResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import com.vanphong.foodnfitbe.utils.MealTypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
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
    public FoodLogResponse createFoodLog(FoodLogRequest request) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String meal = request.getMeal().toUpperCase();

        FoodLog foodLog = foodLogRepository
                .findByUserIdAndDateAndMeal(userId, today, meal)
                .orElseGet(() -> FoodLog.builder()
                        .user(user)
                        .meal(meal)
                        .date(today)
                        .details(new ArrayList<>())
                        .build()
                );

        foodLog.setTotalCalories(request.getTotalCalories());
        foodLog.setTotalProtein(request.getTotalProtein());
        foodLog.setTotalFat(request.getTotalFat());
        foodLog.setTotalCarbs(request.getTotalCarbs());

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

    @Override
    public WeeklyNutritionResponse getWeeklySummary(LocalDate date) {
        UUID userId = currentUser.getCurrentUserId();
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // 2. Lấy tất cả các log trong tuần từ repository
        List<FoodLog> logsInWeek = foodLogRepository.findByUserIdAndDateBetween(userId, startOfWeek, endOfWeek);

        if (logsInWeek.isEmpty()) {
            return WeeklyNutritionResponse.builder()
                    .totalWeeklyCalories(0.0)
                    .mealSummaries(Collections.emptyList())
                    .dailyCalorieChartData(Collections.emptyList())
                    .build();
        }

        double totalWeeklyCalories = logsInWeek.stream()
                .mapToDouble(FoodLog::getTotalCalories)
                .sum();

        Map<String, Double> caloriesByMeal = logsInWeek.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getMeal().trim().toUpperCase(),
                        Collectors.summingDouble(FoodLog::getTotalCalories)
                ));
        List<MealTypeSummaryDto> mealSummaries = new ArrayList<>();
        // Định nghĩa các bữa ăn chuẩn
        List<String> mealTypes = Arrays.asList("BREAKFAST", "LUNCH", "DINNER", "SNACK");
        for (String mealType : mealTypes) {
            Double mealCalories = caloriesByMeal.getOrDefault(mealType, 0.0);
            double percentage = (totalWeeklyCalories > 0) ? (mealCalories / totalWeeklyCalories) * 100 : 0;
            mealSummaries.add(new MealTypeSummaryDto(mealType, mealCalories, percentage));
        }

        // 5. Tổng hợp calo theo từng ngày để vẽ biểu đồ
        Map<LocalDate, Double> caloriesByDay = logsInWeek.stream()
                .collect(Collectors.groupingBy(
                        FoodLog::getDate,
                        Collectors.summingDouble(FoodLog::getTotalCalories)
                ));

        List<DailyCalorieDataDto> dailyChartData = new ArrayList<>();
        // Lặp qua 7 ngày trong tuần để đảm bảo biểu đồ luôn có 7 điểm dữ liệu
        for (LocalDate d = startOfWeek; !d.isAfter(endOfWeek); d = d.plusDays(1)) {
            dailyChartData.add(new DailyCalorieDataDto(d, caloriesByDay.getOrDefault(d, 0.0)));
        }

        // 6. (Tùy chọn) Chuyển đổi danh sách logs chi tiết sang DTO
        // List<FoodLogResponse> detailedLogs = logsInWeek.stream().map(foodLogMapper::toDto).collect(Collectors.toList());

        // 7. Xây dựng và trả về response
        return WeeklyNutritionResponse.builder()
                .totalWeeklyCalories(totalWeeklyCalories)
                .mealSummaries(mealSummaries)
                .dailyCalorieChartData(dailyChartData)
                //.weeklyLogs(detailedLogs) // Bỏ comment nếu bạn muốn trả về log chi tiết
                .build();
    }
}
