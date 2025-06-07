package com.vanphong.foodnfitbe.utils;

import java.util.Arrays;
import java.util.List;

public class MealTypeOrder {
    private static final List<String> mealTypes = List.of("Breakfast", "Lunch", "Dinner", "Snack");

    public static int getOrder(String mealType) {
        return mealTypes.indexOf(mealType);
    }
}
