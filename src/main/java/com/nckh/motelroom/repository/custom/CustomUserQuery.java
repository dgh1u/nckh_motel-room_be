package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;
import com.nckh.motelroom.model.Role;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.utils.CriteriaBuilderUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserQuery {
    private CustomUserQuery(){}

    @Data
    @NoArgsConstructor
    public static class UserFilterParam {
        private String keywords;
        private Boolean block;
        private Collection<Integer> roles;
        private String sortField;
        private String sortType;
    }

    public static Specification<User> getFilterUser(UserFilterParam param) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (param.keywords != null) {
                predicates.add(CriteriaBuilderUtil.createPredicateForSearchInsensitive(root, criteriaBuilder,
                        param.keywords, "fullName"));
            }
            if (param.block != null) {
                predicates.add(criteriaBuilder.equal(root.get("block"), param.block));
            }
            if (!CollectionUtils.isEmpty(param.roles)) {
                Join<User, Role> roleJoin = root.joinList("roles");
                predicates.add(roleJoin.get("id").in(param.roles));
            }
            if (param.sortField != null && !param.sortField.equals("")) {
                if (param.sortType.equals(Constant.SortType.DESC) || param.sortType.equals("")) {
                    query.orderBy(criteriaBuilder.desc(root.get(param.sortField)));
                }
                if (param.sortType.equals(Constant.SortType.ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get(param.sortField)));
                }
            } else {
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}
