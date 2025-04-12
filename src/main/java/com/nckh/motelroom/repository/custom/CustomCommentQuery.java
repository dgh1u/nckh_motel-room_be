package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;
import com.nckh.motelroom.model.Comment;
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

public class CustomCommentQuery {
    private CustomCommentQuery(){}

    @Data
    @NoArgsConstructor
    public static class CommentFilterParam {
        private String sortField;
        private String sortType;
        private Post postId;
    }

    public static Specification<Comment> getFilterComment(CommentFilterParam param) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo userId
            if (param.postId != null) {
                predicates.add(criteriaBuilder.equal(root.get("post"), param.postId));
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
