package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EdamamService {
    private final FoodItemRepository foodItemRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${edamam.id}")
    private String appId;

    @Value("${edamam.key}")
    private String appKey;
    @Value("${edamam.id}")
    private String userId;

    private static final String BASE_URL = "https://api.edamam.com/api/recipes/v2";
    private static final String TYPE = "public";

    public List<FoodItem> fetchAndSaveVietnameseFoods(int targetCount) {
        List<String> keywords = List.of(
                "pho", "bun", "mien", "banh mi", "com tam", "goi cuon", "cha gio", "banh xeo",
                "hu tieu", "bun bo hue", "bun rieu", "banh cuon", "ca kho to", "canh chua",
                "thit kho tau", "com ga", "bo kho", "che", "banh da cua", "bun thit nuong",
                "bun cha", "bun dau mam tom", "ca phe trung", "banh bao", "banh hoi",
                "bun oc", "banh bot loc", "bun mang vit", "com chay", "banh beo",
                "nem ran", "banh trang tron", "banh tet", "xoi", "bun ga nuong",
                "bun moc", "com hen", "ca hap bia", "ca chien", "bun oc nguoi",
                "rau muong xao toi", "bot chien", "goi ngo sen", "mi quang", "ca phe sua da",
                "banh canh", "banh khot", "bun thang", "bot loc", "cha ca"
        );

        List<FoodItem> resultList = new ArrayList<>();
        int count = 0;

        for (String keyword : keywords) {
            if (count >= targetCount) break;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("type", TYPE)
                    .queryParam("q", keyword)
                    .queryParam("app_id", appId)
                    .queryParam("app_key", appKey);

            String nextUrl = builder.toUriString();

            while (count < targetCount && nextUrl != null) {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Edamam-Account-User", userId);
                    HttpEntity<String> entity = new HttpEntity<>(headers);

                    ResponseEntity<String> response = restTemplate.exchange(nextUrl, HttpMethod.GET, entity, String.class);

                    if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                        break;
                    }

                    JSONObject json = new JSONObject(response.getBody());
                    JSONArray hits = json.getJSONArray("hits");

                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject recipeJson = hits.getJSONObject(i).getJSONObject("recipe");
                        FoodItem foodItem = mapRecipeToFoodItem(recipeJson);
                        resultList.add(foodItem);
                        count++;
                        if (count >= targetCount) break;
                    }

                    if (json.has("_links") && json.getJSONObject("_links").has("next")) {
                        nextUrl = json.getJSONObject("_links").getJSONObject("next").getString("href");
                    } else {
                        nextUrl = null;
                    }

                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        foodItemRepository.saveAll(resultList);

        return resultList;
    }
    private FoodItem mapRecipeToFoodItem(JSONObject recipeJson) {
        FoodItem foodItem = new FoodItem();

        foodItem.setNameEn(recipeJson.optString("label"));
        foodItem.setCalories(recipeJson.optDouble("calories"));
        JSONObject nutrients = recipeJson.optJSONObject("totalNutrients");
        if (nutrients != null) {
            foodItem.setProtein(getNutrientValue(nutrients, "PROCNT"));
            foodItem.setCarbs(getNutrientValue(nutrients, "CHOCDF"));
            foodItem.setFat(getNutrientValue(nutrients, "FAT"));
        }
        foodItem.setImageUrl(recipeJson.optString("image"));
        double servings = recipeJson.optDouble("yield");
        foodItem.setServingSizeEn(String.valueOf(servings));
        double totalWeight = recipeJson.optDouble("totalWeight");
        double gramsPerServing = (servings > 0) ? totalWeight / servings : totalWeight;
        foodItem.setServingWeight(gramsPerServing);
        foodItem.setRecipeEn(recipeJson.optString("url"));
        foodItem.setIngredientsEn(joinIngredients(recipeJson.optJSONArray("ingredientLines")));
        foodItem.setActive(true);
        foodItem.setCreatedDate(LocalDate.now());

        // Tạm thời copy tiếng Anh sang tiếng Việt
        foodItem.setNameVi(foodItem.getNameEn());
        foodItem.setRecipeVi(foodItem.getRecipeEn());
        foodItem.setServingSizeVi(foodItem.getServingSizeEn());

        foodItem.setFoodTypeId(1); // có thể thay đổi tùy ý

        return foodItem;
    }

    private Double getNutrientValue(JSONObject nutrients, String key) {
        if (nutrients.has(key)) {
            JSONObject nutrient = nutrients.getJSONObject(key);
            return nutrient.optDouble("quantity");
        }
        return null;
    }

    private String joinIngredients(JSONArray ingredients) {
        if (ingredients == null) return null;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ingredients.length(); i++) {
            list.add(ingredients.getString(i));
        }
        return String.join(", ", list);
    }


    public String scrapeRecipeInstructionsFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            // --- CẬP NHẬT SELECTOR ĐỂ CHÍNH XÁC HƠN ---
            // Dựa trên hình ảnh mới, chúng ta có thể thấy tất cả các bước hướng dẫn
            // đều nằm trong thẻ <div id="recipeDirectionsRoot">.
            // Selector "#recipeDirectionsRoot li.recipe__list-step" có nghĩa là:
            // "Tìm tất cả các thẻ <li> có class 'recipe__list-step'
            // nằm BÊN TRONG một phần tử có id là 'recipeDirectionsRoot'".
            // Đây là cách chọn rất an toàn và cụ thể.
            Elements steps = doc.select("#recipeDirectionsRoot li.recipe__list-step");

            // Nếu selector trên vì lý do nào đó không hoạt động, selector cũ vẫn là một lựa chọn tốt
            if (steps.isEmpty()) {
                steps = doc.select("li.recipe__list-step");
            }

            StringBuilder sb = new StringBuilder();
            for (Element step : steps) {
                // Logic này vẫn giữ nguyên vì nó đã đúng
                sb.append("• ").append(step.text()).append("\n");
            }

            String result = sb.toString().trim();

            if (result.isEmpty()) {
                return "Instructions not found with the specified selectors.";
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load instructions from URL.";
        }
    }

}
