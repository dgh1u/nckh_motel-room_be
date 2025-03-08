package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.Role;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.utils.CriteriaBuilderUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserQuery {
    private CustomUserQuery(){}

    @Data
    @NoArgsConstructor
    public static class UserFilterParam {
        private String keywords;
        private Boolean block;
        private String roleId;
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
            if (param.roleId!=null) {
                Join<User, Role> userJoin = root.join("role");
                predicates.add(criteriaBuilder.equal(userJoin.get("id"), (param.roleId)));
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

    @Data
    @NoArgsConstructor
    public static class PostFilterParam {
        private String title;
        private Boolean approved;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String type;
        private Long userId;
    }

    public static Specification<Post> getFilterPost(PostFilterParam param) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo title
            if (param.getTitle() != null && !param.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + param.getTitle().toLowerCase() + "%"));
            }

            // Lọc theo trạng thái approved
            if (param.getApproved() != null) {
                predicates.add(criteriaBuilder.equal(root.get("approved"), param.getApproved()));
            }

            // Lọc theo ngày tạo (startDate, endDate)
            if (param.getStartDate() != null && param.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("createAt"),
                        param.getStartDate(), param.getEndDate()));
            } else if (param.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), param.getStartDate()));
            } else if (param.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), param.getEndDate()));
            }

            // Lọc theo type
//            if (param.getType() != null && !param.getType().isEmpty()) {
//                predicates.add(criteriaBuilder.equal(root.get("type"), param.getType()));
//            }

            // Lọc theo userId (nếu cần thiết, kết nối với bảng User)
            if (param.getUserId() != null) {
                Join<Post, User> userJoin = root.join("user");
                predicates.add(criteriaBuilder.equal(userJoin.get("id"), param.getUserId()));
            }

            // Áp dụng các điều kiện lọc
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
