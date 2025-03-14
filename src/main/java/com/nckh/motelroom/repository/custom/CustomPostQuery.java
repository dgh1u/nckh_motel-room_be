package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.utils.CriteriaBuilderUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomPostQuery {

    private CustomPostQuery (){};

    @Data
    @NoArgsConstructor
    public static class PostFilterParam {
        private String title;
        private Boolean approved;
        private Boolean notApproved;
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
            if (param.getNotApproved() != null) {
                predicates.add(criteriaBuilder.equal(root.get("not_approved"), param.getNotApproved()));
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
