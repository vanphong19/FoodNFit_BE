package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.FoodLogDetailBatchRequest;

public interface FoodLogDetailService {
    void saveAll(FoodLogDetailBatchRequest request);
    void delete(Integer id);
}