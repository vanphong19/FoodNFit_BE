package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodLogResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WeeklyExerciseSummaryResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanResponse;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutPlanService
{
    WorkoutPlanResponse create(WorkoutPlanRequest request);
    WorkoutPlanResponse update(Integer id, WorkoutPlanRequest request);
    void delete(Integer id);
    WorkoutPlanResponse getByDate(LocalDate date);
    WeeklyExerciseSummaryResponse getWeeklyExerciseSummary(LocalDate date);
}
