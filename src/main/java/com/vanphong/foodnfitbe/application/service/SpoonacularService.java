package com.vanphong.foodnfitbe.application.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.SpoonacularRecipeResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.SpoonacularSearchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class SpoonacularService {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    @Value("${spoonacular.api.base-url:https://api.spoonacular.com/recipes}")
    private String baseUrl;

    private final FoodItemRepository foodItemRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(SpoonacularService.class);

    public SpoonacularService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void fetchAndSave500Recipes() {
        log.info("Bắt đầu lấy 500 món ăn từ Spoonacular...");

        Set<Integer> fetchedIds = new HashSet<>();
        int savedCount = 0;
        int offset = 0;
        int batchSize = 100; // Spoonacular giới hạn 100 kết quả mỗi lần

        while (savedCount < 500 && offset < 5000) { // Giới hạn tối đa 5000 để tránh vòng lặp vô hạn
            try {
                // Tìm kiếm recipes với các criteria khác nhau để đa dạng hóa
                String[] cuisines = {"american", "italian", "asian", "mexican", "mediterranean", "indian"};
                String[] types = {"main course", "dessert", "appetizer", "breakfast", "soup", "salad"};

                String cuisine = cuisines[offset / 100 % cuisines.length];
                String type = types[(offset / 100) % types.length];

                List<Integer> recipeIds = searchRecipes(cuisine, type, offset, batchSize);

                for (Integer recipeId : recipeIds) {
                    if (savedCount >= 500) break;
                    if (fetchedIds.contains(recipeId) || foodItemRepository.existsById(recipeId)) {
                        continue;
                    }

                    try {
                        SpoonacularRecipeResponse recipe = getRecipeDetails(recipeId);
                        FoodItem foodItem = convertToFoodItem(recipe);

                        // Kiểm tra tất cả các trường bắt buộc có giá trị
                        if (isValidFoodItem(foodItem)) {
                            foodItemRepository.save(foodItem);
                            fetchedIds.add(recipeId);
                            savedCount++;
                            log.info("Đã lưu món ăn #{}: {} - Total: {}/500",
                                    savedCount, foodItem.getNameEn(), savedCount);
                        }

                        // Delay để tránh rate limit
                        Thread.sleep(100);

                    } catch (Exception e) {
                        log.warn("Lỗi khi xử lý recipe ID {}: {}", recipeId, e.getMessage());
                    }
                }

                offset += batchSize;

            } catch (Exception e) {
                log.error("Lỗi trong batch offset {}: {}", offset, e.getMessage());
                offset += batchSize;
            }
        }

        log.info("Hoàn thành! Đã lưu {} món ăn vào database", savedCount);
    }

    private List<Integer> searchRecipes(String cuisine, String type, int offset, int number) {
        String url = String.format("%s/complexSearch?apiKey=%s&cuisine=%s&type=%s&offset=%d&number=%d&addRecipeInformation=false",
                baseUrl, apiKey, cuisine, type, offset, number);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            SpoonacularSearchResponse searchResponse = objectMapper.readValue(response.getBody(), SpoonacularSearchResponse.class);

            return searchResponse.getResults().stream()
                    .map(SpoonacularSearchResponse.Recipe::getId)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Lỗi khi search recipes: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private SpoonacularRecipeResponse getRecipeDetails(Integer recipeId) throws Exception {
        String url = String.format("%s/%d/information?apiKey=%s&includeNutrition=true",
                baseUrl, recipeId, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return objectMapper.readValue(response.getBody(), SpoonacularRecipeResponse.class);
    }

    private FoodItem convertToFoodItem(SpoonacularRecipeResponse recipe) {
        FoodItem foodItem = new FoodItem();

        foodItem.setId(recipe.getId());
        foodItem.setNameEn(recipe.getTitle());
        foodItem.setImageUrl(recipe.getImage());
        foodItem.setActive(true);
        foodItem.setFoodTypeId(1); // Default food type

        // Lấy thông tin dinh dưỡng
        if (recipe.getNutrition() != null && recipe.getNutrition().getNutrients() != null) {
            for (SpoonacularRecipeResponse.Nutrition.Nutrient nutrient : recipe.getNutrition().getNutrients()) {
                switch (nutrient.getName().toLowerCase()) {
                    case "calories":
                        foodItem.setCalories(nutrient.getAmount());
                        break;
                    case "protein":
                        foodItem.setProtein(nutrient.getAmount());
                        break;
                    case "carbohydrates":
                        foodItem.setCarbs(nutrient.getAmount());
                        break;
                    case "fat":
                        foodItem.setFat(nutrient.getAmount());
                        break;
                }
            }
        }

        // Tạo serving size
        if (recipe.getServings() != null && recipe.getReadyInMinutes() != null) {
            foodItem.setServingSizeEn(String.format("Serves %d people, Ready in %d minutes",
                    recipe.getServings(), recipe.getReadyInMinutes()));
        }

        // Tạo recipe từ instructions
        if (recipe.getAnalyzedInstructions() != null && !recipe.getAnalyzedInstructions().isEmpty()) {
            StringBuilder recipeBuilder = new StringBuilder();
            for (SpoonacularRecipeResponse.Instruction instruction : recipe.getAnalyzedInstructions()) {
                if (instruction.getSteps() != null) {
                    for (SpoonacularRecipeResponse.Instruction.Step step : instruction.getSteps()) {
                        recipeBuilder.append(step.getNumber()).append(". ")
                                .append(step.getStep()).append("\n");
                    }
                }
            }
            foodItem.setRecipeEn(recipeBuilder.toString().trim());
        }

        // Tạo ingredients list
        if (recipe.getExtendedIngredients() != null) {
            String ingredients = recipe.getExtendedIngredients().stream()
                    .map(SpoonacularRecipeResponse.ExtendedIngredient::getOriginal)
                    .collect(Collectors.joining(", "));
            foodItem.setIngredientsEn(ingredients);
        }

        // Set default Vietnamese fields (có thể integrate với translation API sau)
        foodItem.setNameVi(recipe.getTitle()); // Placeholder
        foodItem.setRecipeVi(foodItem.getRecipeEn()); // Placeholder
        foodItem.setServingSizeVi(foodItem.getServingSizeEn()); // Placeholder
        foodItem.setCreatedDate(LocalDate.now());

        return foodItem;
    }

    private boolean isValidFoodItem(FoodItem item) {
        return item.getNameEn() != null && !item.getNameEn().trim().isEmpty() &&
                item.getCalories() != null && item.getCalories() > 0 &&
                item.getProtein() != null && item.getProtein() > 0 &&
                item.getCarbs() != null && item.getCarbs() > 0 &&
                item.getFat() != null && item.getFat() > 0 &&
                item.getImageUrl() != null && !item.getImageUrl().trim().isEmpty() &&
                item.getRecipeEn() != null && !item.getRecipeEn().trim().isEmpty() &&
                item.getIngredientsEn() != null && !item.getIngredientsEn().trim().isEmpty();
    }
}