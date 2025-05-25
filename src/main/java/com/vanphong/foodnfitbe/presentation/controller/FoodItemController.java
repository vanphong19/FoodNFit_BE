package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FoodItemService;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodItemRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-item")
@RequiredArgsConstructor
public class FoodItemController {
    private final FoodItemService foodItemService;
    @PostMapping("/create")
    public ResponseEntity<FoodItemResponse> create(@RequestBody FoodItemRequest foodItemRequest) {
        FoodItemResponse response = foodItemService.addFoodItem(foodItemRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<FoodItemResponse>> getAll() {
        List<FoodItemResponse> foodItemResponses = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(foodItemResponses);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FoodItemResponse> update(@PathVariable int id, @RequestBody FoodItemRequest foodItemRequest) {
        FoodItemResponse response = foodItemService.updateFoodItem(id, foodItemRequest);
        return ResponseEntity.ok(response);
    }
}
