package com.vanphong.foodnfitbe.domain.specification;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.FoodItem;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FoodSpecification {
    public static Specification<FoodItem> getFoodItemsByCriteria(SearchCriteria criteria) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getSearch() != null && !criteria.getSearch().isEmpty()) {
                String pattern = "%" + criteria.getSearch().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("nameEn")), pattern);
                Predicate nameViPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("nameVi")), pattern);
                predicates.add(criteriaBuilder.or(namePredicate, nameViPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}

