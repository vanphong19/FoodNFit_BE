package com.vanphong.foodnfitbe.presentation.controller;

import com.vanphong.foodnfitbe.application.service.FoodTypeService;
import com.vanphong.foodnfitbe.domain.entity.FoodType;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodTypeRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.FoodTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-type")
@RequiredArgsConstructor
public class FoodTypeController {
    private final FoodTypeService foodTypeService;
    @PostMapping("/create")
    public ResponseEntity<FoodTypeResponse> create(@RequestBody FoodTypeRequest request) {
        FoodTypeResponse response = foodTypeService.addFoodType(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<FoodTypeResponse>> getAll() {
        List<FoodTypeResponse> list = foodTypeService.getAllFoodTypes();
        return ResponseEntity.ok(list);
    }
    @PutMapping("/update")
    public ResponseEntity<FoodTypeResponse> update(Integer id, @RequestBody FoodTypeRequest request) {
        FoodTypeResponse response = foodTypeService.updateFoodType(id, request);
        return ResponseEntity.ok(response);
    }
}
