package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.ExerciseService;
import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.repository.ExerciseRepository;
import com.vanphong.foodnfitbe.presentation.mapper.ExerciseMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ExerciseRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.vanphong.foodnfitbe.utils.YouTubeUtils.extractYouTubeVideoId;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }
    @Override
    public ExerciseResponse createExercise(ExerciseRequest exerciseVModel) {
        String rawUrl = exerciseVModel.getVideoUrl();
        String videoId = extractYouTubeVideoId(rawUrl);

        exerciseVModel.setVideoUrl(videoId);

        Exercise exercise = ExerciseMapper.toEntity(exerciseVModel);
        Exercise saved = exerciseRepository.saveExercise(exercise);
        return ExerciseMapper.toResponse(saved);
    }

    @Override
    public List<ExerciseResponse> getAllExercises() {
        List<Exercise> exerciseResponseList = exerciseRepository.findAllExercises();
        return ExerciseMapper.toResponses(exerciseResponseList);
    }

    @Override
    public ExerciseResponse updateExercise(Integer id, ExerciseRequest request) {
        Optional<Exercise> optional = exerciseRepository.findExerciseById(id);
        if(optional.isEmpty()){
            throw new RuntimeException("Exercise not found with id: " + id);
        }
        Exercise existing = optional.get();
        existing.setExerciseName(request.getExerciseName());
        existing.setDescription(request.getDescription());
        existing.setVideoUrl(extractYouTubeVideoId(request.getVideoUrl()));
        existing.setImageUrl(request.getImageUrl());
        existing.setDifficultyLevel(request.getDifficultyLevel());
        existing.setMuscleGroup(request.getMuscleGroup());
        existing.setEquipmentRequired(request.getEquipmentRequired());
        existing.setCaloriesBurnt(request.getCaloriesBurnt());
        existing.setMinutes(request.getMinutes());
        existing.setSets(request.getSets());
        existing.setReps(request.getReps());
        existing.setRestTimeSeconds(request.getRestTimeSeconds());
        existing.setNote(request.getNote());
        existing.setExerciseType(request.getExerciseType());
        existing.setActive(request.getActive());

        Exercise updated = exerciseRepository.saveExercise(existing);
        return ExerciseMapper.toResponse(updated);
    }

    @Override
    public void deleteExercise(Integer id) {
        Optional<Exercise> optional = exerciseRepository.findExerciseById(id);
        if(optional.isEmpty()){
            throw new RuntimeException("Exercise not found with id: " + id);
        }

        exerciseRepository.delete(optional.get());
    }

    @Override
    public Optional<ExerciseResponse> getExerciseById(Integer id) {
        Optional<Exercise> optional = exerciseRepository.findExerciseById(id);
        return optional.map(ExerciseMapper::toResponse);
    }

    @Override
    public List<ExerciseResponse> searchExercise(String keyword) {
        List<Exercise> list = exerciseRepository.searchExercise(keyword);
        return ExerciseMapper.toResponses(list);
    }

    @Override
    public Long countExercises() {
        return exerciseRepository.count();
    }

    @Override
    public Long countExerciseCreatedAThisMonth() {
        YearMonth thisMonth = YearMonth.now();

        LocalDate startOfMonth = thisMonth.atDay(1);
        LocalDate endOfMonth = thisMonth.atEndOfMonth();

        return exerciseRepository.countExercisesCreatedThisMonth(startOfMonth, endOfMonth);
    }
}
