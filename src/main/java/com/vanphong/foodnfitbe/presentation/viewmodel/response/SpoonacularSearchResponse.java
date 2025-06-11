package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.Data;

import java.util.List;

@Data
public class SpoonacularSearchResponse {
    private List<Recipe> results;
    private Integer totalResults;

    @Data
    public static class Recipe {
        private Integer id;
        private String title;
        private String image;
    }
}