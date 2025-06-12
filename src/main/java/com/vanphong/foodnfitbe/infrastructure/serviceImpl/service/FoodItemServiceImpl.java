package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.FoodItemService;
import com.vanphong.foodnfitbe.application.service.TranslateService;
import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.domain.specification.ExerciseSpecification;
import com.vanphong.foodnfitbe.domain.specification.FoodSpecification;
import com.vanphong.foodnfitbe.presentation.mapper.FoodItemMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodItemRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodItemResponse;
import jakarta.transaction.Transactional;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final TranslateService translateService;
    private final FoodItemMapper foodItemMapper;

    @Override
    public FoodItemResponse addFoodItem(FoodItemRequest request) {
        String nameVi = translateService.translateToVietnamese(request.getNameEn());
        String recipeVi = translateService.translateToVietnamese(request.getRecipeEn());
        String servingSizeVi = translateService.translateToVietnamese(request.getServingSizeEn());

        FoodItem foodItem = FoodItem.builder()
                .nameEn(request.getNameEn())
                .nameVi(nameVi)
                .recipeEn(request.getRecipeEn())
                .recipeVi(recipeVi)
                .servingSizeEn(request.getServingSizeEn())
                .servingSizeVi(servingSizeVi)
                .calories(request.getCalories())
                .fat(request.getFat())
                .carbs(request.getCarbs())
                .protein(request.getProtein())
                .imageUrl(request.getImageUrl())
                .foodTypeId(request.getFoodTypeId())
                .active(true)
                .build();

        FoodItem savedFoodItem = foodItemRepository.save(foodItem);
        return foodItemMapper.toResponse(savedFoodItem);
    }

    @Override
    public FoodItemResponse updateFoodItem(Integer id, FoodItemRequest request) {
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);
        if(foodItem.isEmpty()){
            throw new RuntimeException("Food item not found with id: " + id);
        }

        FoodItem existing = foodItem.get();
        existing.setNameEn(request.getNameEn());
        existing.setRecipeEn(request.getRecipeEn());
        existing.setServingSizeEn(request.getServingSizeEn());
        existing.setCalories(request.getCalories());
        existing.setFoodTypeId(request.getFoodTypeId());
        existing.setFat(request.getFat());
        existing.setCarbs(request.getCarbs());
        existing.setProtein(request.getProtein());
        existing.setImageUrl(request.getImageUrl());
        existing.setNameVi(translateService.translateToVietnamese(request.getNameEn()));
        existing.setRecipeVi(translateService.translateToVietnamese(request.getRecipeEn()));
        existing.setServingSizeVi(translateService.translateToVietnamese(request.getServingSizeEn()));
        existing.setFoodTypeId(request.getFoodTypeId());
        FoodItem updated = foodItemRepository.save(existing);
        return foodItemMapper.toResponse(updated);
    }

    @Override
    public FoodItemResponse deleteFoodItem(Integer id) {
        return null;
    }

    @Override
    public Page<FoodItemResponse> getAllFoodItems(SearchCriteria criteria) {
        int page = criteria.getPage() != null && criteria.getPage() > 0 ? criteria.getPage() - 1: 0;
        int size = criteria.getSize() != null && criteria.getSize() > 0 ? criteria.getSize() : 10;

        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "id";
        Sort.Direction direction = "desc".equalsIgnoreCase(criteria.getSortDir()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Specification<FoodItem> spec = FoodSpecification.getFoodItemsByCriteria(criteria);
        return foodItemRepository.findAll(spec, pageRequest).map(foodItemMapper::toResponse);
    }

    @Override
    public Optional<FoodItemResponse> getFoodItemById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Long countFoodCreatedThisMonth() {
        YearMonth thisMonth = YearMonth.now();

        LocalDate startOfMonth = thisMonth.atDay(1);
        LocalDate endOfMonth = thisMonth.atEndOfMonth();

        return foodItemRepository.countFoodCreatedThisMonth(startOfMonth, endOfMonth);
    }
}
