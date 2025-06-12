package com.vanphong.foodnfitbe.domain.specification;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.SearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSpecification {
    public static Specification<Exercise> getExercisesByCriteria(SearchCriteria criteria) {
        return ((root, query, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getSearch() != null && !criteria.getSearch().isEmpty()) {
                String pattern = "%" + criteria.getSearch().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(root.get("exerciseName"), pattern);
                predicates.add(criteriaBuilder.or(namePredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
