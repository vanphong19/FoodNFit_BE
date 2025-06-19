package com.vanphong.foodnfitbe.application.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.SpoonacularRecipeResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.SpoonacularSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpoonacularService {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    @Value("${spoonacular.api.base-url:https://api.spoonacular.com/recipes}")
    private String baseUrl;

    private final FoodItemRepository foodItemRepository;
    private final TranslateService translateService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(SpoonacularService.class);

    public SpoonacularService(FoodItemRepository foodItemRepository, TranslateService translateService) {
        this.foodItemRepository = foodItemRepository;
        this.translateService = translateService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void fetchAndSave500Recipes() {
        log.info("Bắt đầu lấy 500 món ăn Việt Nam từ Spoonacular...");

        Set<Integer> fetchedIds = new HashSet<>();
        int savedCount = 0;
        int offset = 0;
        int batchSize = 100;

        // Từ khóa tìm kiếm tập trung vào món Việt Nam

// Từ khóa tìm kiếm tập trung vào món Việt Nam - Chỉ những món đặc trưng
        String[] vietnameseKeywords = {
                // Món phở - rất đặc trưng VN
                "pho", "pho bo", "pho ga", "vietnamese pho", "vietnam pho",

                // Món bún - đặc trưng VN
                "bun bo hue", "bun rieu", "bun thit nuong", "bun cha", "bun bo nam bo",
                "bun dau mam tom", "bun ca", "bun oc",

                // Miến - với tên Việt
                "mien ga", "mien luon", "mien cua", "vietnamese glass noodles",

                // Hủ tiếu và các món mì đặc trưng
                "hu tieu", "hu tieu nam vang", "mi quang", "cao lau",

                // Cơm tấm - đặc trưng VN
                "com tam", "broken rice", "com tam suon",

                // Xôi - đặc trưng VN
                "xoi", "xoi man", "xoi gac", "vietnamese sticky rice",

                // Bánh mì Việt Nam
                "banh mi", "vietnamese sandwich", "vietnamese baguette",

                // Bánh xèo và các bánh đặc trưng
                "banh xeo", "vietnamese pancake", "banh khot", "banh cuon",
                "banh chung", "banh tet", "banh it", "banh beo", "banh loc",
                "banh canh", "banh uot",

                // Gỏi cuốn - rất đặc trưng VN
                "goi cuon", "vietnamese fresh spring rolls", "vietnamese summer rolls",

                // Chả giò/nem - đặc trưng VN
                "cha gio", "nem ran", "vietnamese fried spring rolls",
                "nem nuong", "nem chua", "nem lui",

                // Canh chua - đặc trưng VN
                "canh chua", "vietnamese sour soup", "canh chua ca",

                // Chả cá Lã Vọng - đặc trưng Hà Nội
                "cha ca", "cha ca la vong", "vietnamese grilled fish",

                // Các món với nước mắm - đặc trưng VN
                "nuoc mam", "vietnamese fish sauce", "nuoc cham",

                // Chè - tráng miệng đặc trưng VN
                "che", "che ba mau", "che dau xanh", "che troi nuoc", "vietnamese sweet soup",

                // Bánh flan kiểu Việt
                "banh flan", "vietnamese flan",

                // Các món đặc trưng khác
                "bo la lot", "vietnamese beef in betel leaves",
                "thit kho", "vietnamese braised pork",
                "ca kho to", "vietnamese braised fish",
                "tom rang me", "vietnamese tamarind shrimp",
                "goi ngo sen", "vietnamese lotus root salad",
                "goi du du", "vietnamese green papaya salad",

                // Từ khóa tổng quát nhưng an toàn
                "vietnamese cuisine", "authentic vietnamese", "traditional vietnamese",
                "vietnam recipe", "vietnamese dish"
        };

        // Cuisine types ưu tiên Việt Nam
        String[] cuisines = {
                "vietnamese",     // Ưu tiên cao nhất
                "vietnamese",     // Lặp lại để tăng tỷ lệ
                "vietnamese",
                        // Một số món gốc Hoa ảnh hưởng VN
        };

        // Types tập trung vào các loại món Việt thường gặp
        String[] types = {
                "main course",    // Món chính
                "soup",           // Phở, bún, canh
                "noodle",         // Các món bún, miến, phở
                "rice",           // Cơm tấm, cơm chiên
                "appetizer",      // Gỏi cuốn, nem
                "salad",          // Gỏi, nộm
                "breakfast",      // Phở sáng, bánh mì
                "lunch",          // Cơm trưa
                "dinner",         // Cơm tối
                "snack",          // Chè, bánh tráng
                "side dish",      // Rau luộc, đậu phụ
                "seafood",        // Chả cá, tôm, cua
                "vegetarian",     // Món chay Việt
                "dessert"         // Chè, bánh flan
        };

        int keywordIndex = 0;

        while (savedCount < 500 && keywordIndex < vietnameseKeywords.length) {
            try {
                // Tìm kiếm theo từ khóa Việt Nam cụ thể
                String keyword = vietnameseKeywords[keywordIndex];
                List<Integer> recipeIds = searchRecipesByKeyword(keyword, offset, batchSize);

                // Nếu không tìm thấy đủ với keyword, thử với cuisine + type
                if (recipeIds.isEmpty() && keywordIndex < cuisines.length * types.length) {
                    String cuisine = cuisines[keywordIndex % cuisines.length];
                    String type = types[keywordIndex % types.length];
                    recipeIds = searchRecipes(cuisine, type, offset, batchSize);
                }

                for (Integer recipeId : recipeIds) {
                    if (savedCount >= 500) break;
                    if (fetchedIds.contains(recipeId) || foodItemRepository.existsById(recipeId)) {
                        continue;
                    }

                    try {
                        SpoonacularRecipeResponse recipe = getRecipeDetails(recipeId);

                        // Kiểm tra xem có phải món Việt không dựa trên tên và ingredients
                        if (isVietnameseFood(recipe)) {
                            FoodItem foodItem = convertToFoodItem(recipe);

                            if (isValidFoodItem(foodItem)) {
                                foodItemRepository.save(foodItem);
                                fetchedIds.add(recipeId);
                                savedCount++;
                                log.info("Đã lưu món Việt #{}: {} - Total: {}/500",
                                        savedCount, foodItem.getNameEn(), savedCount);
                            }
                        }

                        Thread.sleep(150); // Tăng delay để tránh rate limit

                    } catch (Exception e) {
                        log.warn("Lỗi khi xử lý recipe ID {}: {}", recipeId, e.getMessage());
                    }
                }

                keywordIndex++;
                if (keywordIndex % 10 == 0) { // Reset offset mỗi 10 keywords
                    offset = 0;
                } else {
                    offset += batchSize;
                }

            } catch (Exception e) {
                log.error("Lỗi khi tìm kiếm với keyword {}: {}",
                        keywordIndex < vietnameseKeywords.length ? vietnameseKeywords[keywordIndex] : "unknown",
                        e.getMessage());
                keywordIndex++;
            }
        }

        log.info("Hoàn thành! Đã lưu {} món ăn Việt Nam vào database", savedCount);
    }

    // Thêm method tìm kiếm theo keyword
    private List<Integer> searchRecipesByKeyword(String keyword, int offset, int number) {
        String url = String.format("%s/complexSearch?apiKey=%s&query=%s&offset=%d&number=%d&addRecipeInformation=false",
                baseUrl, apiKey, keyword.replace(" ", "+"), offset, number);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            SpoonacularSearchResponse searchResponse = objectMapper.readValue(response.getBody(), SpoonacularSearchResponse.class);

            return searchResponse.getResults().stream()
                    .map(SpoonacularSearchResponse.Recipe::getId)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Lỗi khi search recipes với keyword '{}': {}", keyword, e.getMessage());
            return new ArrayList<>();
        }
    }

    // Method kiểm tra xem có phải món Việt không
    private boolean isVietnameseFood(SpoonacularRecipeResponse recipe) {
        String title = recipe.getTitle().toLowerCase();
        String summary = recipe.getSummary() != null ? recipe.getSummary().toLowerCase() : "";

        // Từ khóa đặc trưng món Việt
        String[] vietnameseIndicators = {
                "vietnam", "vietnamese", "pho", "banh", "bun", "com", "nuoc mam",
                "fish sauce", "goi cuon", "spring roll", "nem", "cha gio",
                "mien", "cao lau", "mi quang", "hu tieu", "che", "xoi",
                "canh chua", "tom", "cua", "muc", "cha ca", "thit nuong"
        };

        // Kiểm tra title
        for (String indicator : vietnameseIndicators) {
            if (title.contains(indicator)) {
                return true;
            }
        }

        // Kiểm tra summary nếu có
        for (String indicator : vietnameseIndicators) {
            if (summary.contains(indicator)) {
                return true;
            }
        }

        // Kiểm tra ingredients
        if (recipe.getExtendedIngredients() != null) {
            for (var ingredient : recipe.getExtendedIngredients()) {
                String ingredientName = ingredient.getName().toLowerCase();
                if (ingredientName.contains("fish sauce") ||
                        ingredientName.contains("rice paper") ||
                        ingredientName.contains("vietnamese") ||
                        ingredientName.contains("nuoc mam")) {
                    return true;
                }
            }
        }

        return false;
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
        if (recipe.getServings() != null) {
            foodItem.setServingSizeEn(String.format("%d people", recipe.getServings()));
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
        foodItem.setNameVi(translateService.translateToVietnamese(recipe.getTitle()));
        foodItem.setRecipeVi(translateService.translateToVietnamese(foodItem.getRecipeEn()));
        foodItem.setServingSizeVi(translateService.translateToVietnamese(foodItem.getServingSizeEn()));
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