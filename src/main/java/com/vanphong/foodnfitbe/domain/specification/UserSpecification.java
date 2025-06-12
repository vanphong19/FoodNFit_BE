package com.vanphong.foodnfitbe.domain.specification;

import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<Users> getUsersByCriteria(UserSearchCriteria criteria) {
        return ((root, query, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getSearch() != null && !criteria.getSearch().isEmpty()) {
                String pattern = "%" + criteria.getSearch().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(root.get("fullname"), pattern);
                Predicate emailPredicate = criteriaBuilder.like(root.get("email"), pattern);
                predicates.add(criteriaBuilder.or(namePredicate, emailPredicate));
            }

            if(criteria.getGender() != null){
                predicates.add(criteriaBuilder.equal(root.get("gender"), criteria.getGender()));
            }

            if(criteria.getBlock() != null){
                predicates.add(criteriaBuilder.equal(root.get("blocked"), criteria.getBlock()));
            }

            if(predicates.isEmpty()) {
                return criteriaBuilder.conjunction();  // trả về true, tức lấy tất cả
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }
}
