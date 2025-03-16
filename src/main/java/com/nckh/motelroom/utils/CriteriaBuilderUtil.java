package com.nckh.motelroom.utils;

import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CriteriaBuilderUtil {
    public static <T> Predicate createPredicateForSearchInsensitive(Root<T> root,
                                                                    CriteriaBuilder criteriaBuilder,
                                                                    String keyword,
                                                                    String... fieldNames) {
        List<Predicate> predicates = new ArrayList<>();
        if (fieldNames != null && fieldNames.length > 0) {
            for (String fieldName : fieldNames) {
                predicates.add(criteriaBuilder
                        .like(criteriaBuilder.lower(root.get(fieldName)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));
            }
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    public static <T, U> Predicate createPredicateForSearchInsensitive(Join<T, U> join, CriteriaBuilder criteriaBuilder, String keywords, String fieldName) {
        List<Predicate> predicates = new ArrayList<>();
        if (fieldName != null && !fieldName.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(join.get(fieldName)),
                    "%" + keywords.toLowerCase(Locale.ROOT) + "%"
            ));
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

}
