package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;
import com.nckh.motelroom.model.Action;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomActionQuery {
    private CustomActionQuery() {
    }

    @Data
    @NoArgsConstructor
    public static class ActionFilterParam {
        private String sortField;
        private String sortType;

        private Long userId;
        // Truyền ID người dùng nếu muốn lọc ACTION tạo/bị tác động bởi user này (VD: action.user.id = userId)

        private String postIds;
        // Dạng CSV, ví dụ: "39,38,29"
        // FE sẽ truyền ?postIds=39,38,29 (không dùng [] để tránh lỗi Tomcat)
    }

    public static Specification<Action> getFilterAction(ActionFilterParam param) {
        return (root, query, criteriaBuilder) -> {
            // Nếu query không phải count query, thực hiện fetch join để load user & post (giảm lazy-loading)
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("user", JoinType.LEFT);
                root.fetch("post", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();

            // 1) Lọc theo userId (nếu có)
            if (param.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), param.getUserId()));
            }

            // 2) Lọc theo danh sách postIds (nếu có)
            if (StringUtils.hasText(param.getPostIds())) {
                // Tách CSV => List<Long>
                List<Long> postIdList = new ArrayList<>();
                for (String s : param.getPostIds().split(",")) {
                    try {
                        postIdList.add(Long.parseLong(s.trim()));
                    } catch (NumberFormatException e) {
                        // Nếu parse không được, có thể bỏ qua hoặc ném ngoại lệ
                    }
                }
                // Tạo Predicate: post.id IN ( ... )
                if (!postIdList.isEmpty()) {
                    predicates.add(root.get("post").get("id").in(postIdList));
                }
            }

            // 3) Xử lý sort
            if (param.getSortField() != null && !param.getSortField().isEmpty()) {
                if (param.getSortType() == null
                        || param.getSortType().equals(Constant.SortType.DESC)
                        || param.getSortType().isEmpty()) {

                    query.orderBy(criteriaBuilder.desc(root.get(param.getSortField())));
                } else if (param.getSortType().equals(Constant.SortType.ASC)) {
                    query.orderBy(criteriaBuilder.asc(root.get(param.getSortField())));
                }
            } else {
                // Mặc định sort theo id giảm dần
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
