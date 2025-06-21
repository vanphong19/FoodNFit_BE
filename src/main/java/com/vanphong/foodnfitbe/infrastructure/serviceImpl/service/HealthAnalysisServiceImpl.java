package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.GeminiService;
import com.vanphong.foodnfitbe.application.service.HealthAnalysisService;
import com.vanphong.foodnfitbe.application.service.ReminderService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.entity.WaterIntake;
import com.vanphong.foodnfitbe.domain.repository.*;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class HealthAnalysisServiceImpl implements HealthAnalysisService {
    private final WaterIntakeRepository waterRepo;
    private final StepsTrackingRepository stepsRepo;
    private final FoodLogRepository foodRepo;
    private final WorkoutPlanRepository workoutRepo;
    private final UserProfileRepository profileRepo;
    private final GeminiService geminiService;
    private final ReminderService reminderService;
    private final UserRepository userRepository;
    @Override
    public void analyzeAndCreateReminder(UUID userId) {
        LocalDate startDate = LocalDate.now().minusDays(6);
        LocalDateTime startTime = startDate.atStartOfDay();

        List<WaterIntake> waterList = waterRepo.findLast7DaysWaterIntakeByUserId(userId, startDate);
        List<StepsTracking> stepsList = stepsRepo.findStepsInLast7Days(userId, startTime);
        List<Object[]> foodList = foodRepo.getDailyNutritionSummaryLast7Days(userId, startDate);
        List<Object[]> workoutList = workoutRepo.getTotalCaloriesBurntByDayLast7Days(userId, startDate);
        UserProfiles profile = profileRepo.getLatestByUserID(userId)
                .orElseThrow(() -> new RuntimeException("No profile found"));
        Optional<Users> users = userRepository.findUser(userId);

        if(profile == null){
            return;
        }
        if(users.isEmpty()){
            return;
        }

        Users user = users.get();
        waterList = waterList == null ? Collections.emptyList() : waterList;
        stepsList = stepsList == null ? Collections.emptyList() : stepsList;
        foodList = foodList == null ? Collections.emptyList() : foodList;
        workoutList = workoutList == null ? Collections.emptyList() : workoutList;

        Map<LocalDate, StringBuilder> dayData = new TreeMap<>();

        Map<LocalDate, Integer> totalWaterPerDay = waterList.stream()
                .collect(Collectors.groupingBy(
                        WaterIntake::getDate,
                        Collectors.summingInt(WaterIntake::getCups)
                ));

        for (Map.Entry<LocalDate, Integer> entry : totalWaterPerDay.entrySet()) {
            dayData.computeIfAbsent(entry.getKey(), d -> new StringBuilder())
                    .append("  - Nước uống: ").append(entry.getValue()).append(" cốc\n");
        }

        Map<LocalDate, Integer> totalStepsPerDay = stepsList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStartTime().toLocalDate(),
                        Collectors.summingInt(StepsTracking::getStepsCount)
                ));

        for (Map.Entry<LocalDate, Integer> entry : totalStepsPerDay.entrySet()) {
            dayData.computeIfAbsent(entry.getKey(), d -> new StringBuilder())
                    .append("  - Bước chân: ").append(entry.getValue()).append(" bước\n");
        }

        for (Object[] row : foodList) {
            LocalDate date = (LocalDate) row[0];
            Double calories = toDouble(row[1]);
            Double protein = toDouble(row[2]);
            Double carbs = toDouble(row[3]);
            Double fat = toDouble(row[4]);

            dayData.computeIfAbsent(date, x -> new StringBuilder())
                    .append(String.format("  - Calo: %.0f kcal, Protein: %.0f g, Carb: %.0f g, Fat: %.0f g\n",
                            calories, protein, carbs, fat));
        }


        for (Object[] o : workoutList) {
            LocalDate d = (LocalDate) o[0];
            Double workoutCalories = toDouble(o[1]);
            dayData.computeIfAbsent(d, x -> new StringBuilder())
                    .append(String.format("  - Workout: %.0f kcal\n", workoutCalories));

        }

        StringBuilder prompt = new StringBuilder("Phân tích dữ liệu sức khỏe 7 ngày gần nhất:\n\n");
        if (waterList.isEmpty() && stepsList.isEmpty() && foodList.isEmpty() && workoutList.isEmpty()) {
            prompt.append("Không có dữ liệu sức khỏe nào trong 7 ngày gần nhất để phân tích.\n\n");
        } else {
            for (Map.Entry<LocalDate, StringBuilder> e : dayData.entrySet()) {
                prompt.append("Ngày ").append(e.getKey()).append(":\n")
                        .append(e.getValue()).append("\n");
            }
        }

        boolean gender = user.isGender();
        String genderStr = gender ? "Nữ" : "Nam";
        LocalDate birthday = user.getBirthday();
        int age = 0;
        if (birthday != null) {
            age = Period.between(birthday, LocalDate.now()).getYears();
        }

        prompt.append(String.format("""
            Thông tin người dùng:
            - Cân nặng: %.1f kg
            - Chiều cao: %.1f cm
            - Giới tính: %s
            - Tuổi: %d

            Hãy phân tích xu hướng sức khỏe và đưa ra lời khuyên cải thiện ăn uống, vận động, uống nước.
            """, profile.getWeight(), profile.getHeight(), genderStr, age));

        String result = geminiService.generateHealthReminder(prompt.toString());

        ReminderRequest req = new ReminderRequest();
        req.setReminderType("Phân tích sức khỏe hàng tuần");
        req.setMessage(result);
        req.setScheduledTime(LocalDateTime.now());
        req.setFrequency("analyse");

        reminderService.createReminderForUser(userId, req);
    }
    private Double toDouble(Object obj) {
        if (obj == null) return 0.0;
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
