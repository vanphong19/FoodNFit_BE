package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.ExerciseService;
import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.ExerciseRepository;
import com.vanphong.foodnfitbe.domain.specification.ExerciseSpecification;
import com.vanphong.foodnfitbe.domain.specification.UserSpecification;
import com.vanphong.foodnfitbe.presentation.mapper.ExerciseMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ExerciseRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.vanphong.foodnfitbe.utils.YouTubeUtils.extractYouTubeVideoId;

@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public ExerciseResponse createExercise(ExerciseRequest exerciseVModel) {
        String rawUrl = exerciseVModel.getVideoUrl();
        String videoId = extractYouTubeVideoId(rawUrl);

        exerciseVModel.setVideoUrl(videoId);

        Exercise exercise = exerciseMapper.toEntity(exerciseVModel);
        Exercise saved = exerciseRepository.saveExercise(exercise);
        return exerciseMapper.toResponse(saved);
    }

    @Override
    public Page<ExerciseResponse> getAllExercises(SearchCriteria criteria) {
        int page = criteria.getPage() != null && criteria.getPage() > 0 ? criteria.getPage() - 1: 0;
        int size = criteria.getSize() != null && criteria.getSize() > 0 ? criteria.getSize() : 10;

        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "id";
        Sort.Direction direction = "desc".equalsIgnoreCase(criteria.getSortDir()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Specification<Exercise> spec = ExerciseSpecification.getExercisesByCriteria(criteria);
        return exerciseRepository.findAllExercises(spec, pageRequest).map(exerciseMapper::toResponse);
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
        return exerciseMapper.toResponse(updated);
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
        return optional.map(exerciseMapper::toResponse);
    }

    @Override
    public List<ExerciseResponse> searchExercise(String keyword) {
        List<Exercise> list = exerciseRepository.searchExercise(keyword);
        return exerciseMapper.toResponses(list);
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
