package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.Data;

import java.util.List;

@Data
public class SpoonacularRecipeResponse {
    private Integer id;
    private String title;
    private String image;
    private Integer readyInMinutes;
    private Integer servings;
    private String summary;
    private List<Instruction> analyzedInstructions;
    private List<ExtendedIngredient> extendedIngredients;
    private Nutrition nutrition;

    @Data
    public static class Instruction {
        private List<Step> steps;

        @Data
        public static class Step {
            private Integer number;
            private String step;
        }
    }

    @Data
    public static class ExtendedIngredient {
        private String name;
        private String original;
        private Double amount;
        private String unit;
    }

    @Data
    public static class Nutrition {
        private List<Nutrient> nutrients;

        @Data
        public static class Nutrient {
            private String name;
            private Double amount;
            private String unit;
        }
    }
}
