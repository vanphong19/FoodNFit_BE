package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FoodItemService;
import com.vanphong.foodnfitbe.application.service.SpoonacularService;
import com.vanphong.foodnfitbe.domain.repository.FoodItemRepository;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodItemRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/food-item")
@RequiredArgsConstructor
public class FoodItemController {
    private final FoodItemService foodItemService;
    private final SpoonacularService spoonacularService;
    private static final Logger log = LoggerFactory.getLogger(FoodItemController.class);
    private final FoodItemRepository foodItemRepository;


    @PostMapping("/create")
    public ResponseEntity<FoodItemResponse> create(@RequestBody FoodItemRequest foodItemRequest) {
        FoodItemResponse response = foodItemService.addFoodItem(foodItemRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Page<FoodItemResponse>> getAll(
            @RequestParam(defaultValue = "") String search,  // Mặc định là chuỗi rỗng
            @RequestParam(defaultValue = "0") Integer page,  // Mặc định là 0
            @RequestParam(defaultValue = "10") Integer size,  // Mặc định là 10
            @RequestParam(defaultValue = "id") String sortBy,  // Mặc định là "id"
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        SearchCriteria criteria = SearchCriteria.builder()
                .search(search)
                .size(size)
                .page(page)
                .sortDir(sortDir)
                .sortBy(sortBy).build();
        Page<FoodItemResponse> foodItemResponses = foodItemService.getAllFoodItems(criteria);
        return ResponseEntity.ok(foodItemResponses);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FoodItemResponse> update(@PathVariable int id, @RequestBody FoodItemRequest foodItemRequest) {
        FoodItemResponse response = foodItemService.updateFoodItem(id, foodItemRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<FoodItemResponse> getById(@PathVariable Integer id) {
        FoodItemResponse response = foodItemService.getById(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok("Removed " + id + " has been successfully");
    }

    @PostMapping("/fetch-spoonacular-data")
    public ResponseEntity<Map<String, Object>> fetchSpoonacularData() {
        try {
            spoonacularService.fetchAndSave500Recipes();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Đã bắt đầu quá trình lấy dữ liệu từ Spoonacular");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Lỗi khi fetch data từ Spoonacular: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getFoodCount() {
        try {
            long count = foodItemRepository.count();

            Map<String, Object> response = new HashMap<>();
            response.put("total_foods", count);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/count-this-month")
    public ResponseEntity<Long> getFoodCountThisMonth() {
        Long count = foodItemService.countFoodCreatedThisMonth();
        return ResponseEntity.ok(count);
    }
}
