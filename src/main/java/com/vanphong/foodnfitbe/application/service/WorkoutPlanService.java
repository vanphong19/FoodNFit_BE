package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.*;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutPlanService
{
    WorkoutPlanCreateResponse create(WorkoutPlanRequest request);
    WorkoutPlanResponse update(Integer id, WorkoutPlanRequest request);
    void delete(Integer id);
    WorkoutPlanByDate getByDate(LocalDate date);
    WeeklyExerciseSummaryResponse getWeeklyExerciseSummary(LocalDate date);
}
