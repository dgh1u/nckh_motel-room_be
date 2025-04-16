package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;

import com.nckh.motelroom.model.PaymentHistory;
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

public class CustomPaymentQuery {
    private CustomPaymentQuery(){}

    @Data
    @NoArgsConstructor
    public static class PaymentFilterParam {
        private String sortField;
        private String sortType;
        private Long userId;
        private String startDate;
        private String endDate;
    }

    public static Specification<PaymentHistory> getFilterPayment(PaymentFilterParam param) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (param.userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), param.userId));
            }

            // Lọc theo ngày nạp: so sánh trực tiếp nếu định dạng chuỗi đảm bảo được thứ tự thời gian
            if (param.getStartDate() != null && !param.getStartDate().trim().isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDateTime"), param.getStartDate()));
            }
            if (param.getEndDate() != null && !param.getEndDate().trim().isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDateTime"), param.getEndDate()));
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
        });
    }
}
