package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.District;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.utils.CriteriaBuilderUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomPostQuery {

    private CustomPostQuery() {}

    @Data
    @NoArgsConstructor
    public static class PostFilterParam extends CustomAccomodationQuery.AccomodationFilterParam {
        private String keywords;
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

            // Lọc theo title của Post
            if (param.keywords != null) {
                predicates.add(CriteriaBuilderUtil.createPredicateForSearchInsensitive(root, criteriaBuilder,
                        param.keywords, "title"));
            }

            // Lọc theo trạng thái approved và notApproved
            if (param.getApproved() != null) {
                predicates.add(criteriaBuilder.equal(root.get("approved"), param.getApproved()));
            }
            if (param.getNotApproved() != null) {
                predicates.add(criteriaBuilder.equal(root.get("not_approved"), param.getNotApproved()));
            }

            // Lọc theo ngày tạo
            if (param.getStartDate() != null && param.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("createAt"),
                        param.getStartDate(), param.getEndDate()));
            } else if (param.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), param.getStartDate()));
            } else if (param.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), param.getEndDate()));
            }

            // Lọc theo userId
            if (param.getUserId() != null) {
                Join<Post, User> userJoin = root.join("user");
                predicates.add(criteriaBuilder.equal(userJoin.get("id"), param.getUserId()));
            }

            // Nếu có bất kỳ trường lọc của Accomodation nào được set, thực hiện join với Accomodation
            if (param.getMinPrice() != null || param.getMaxPrice() != null ||
                    param.getMinAcreage() != null || param.getMaxAcreage() != null ||
                    param.getInterior() != null || param.getKitchen() != null ||
                    param.getAirConditioner() != null || param.getHeater() != null ||
                    param.getInternet() != null || param.getOwner() != null ||
                    param.getParking() != null || param.getToilet() != null ||
                    param.getTime() != null || param.getSecurity() != null ||
                    param.getGender() != null ||
                    (param.getDistrictName() != null && !param.getDistrictName().isEmpty()) ||
                    (param.getKeywords() != null && !param.getKeywords().isEmpty())
            ) {
                Join<Post, Accomodation> accomodationJoin = root.join("accomodation", JoinType.LEFT);

                // Lọc theo khoảng giá
                if (param.getMinPrice() != null && param.getMaxPrice() != null) {
                    predicates.add(criteriaBuilder.between(accomodationJoin.get("price"),
                            param.getMinPrice(), param.getMaxPrice()));
                } else if (param.getMinPrice() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(accomodationJoin.get("price"),
                            param.getMinPrice()));
                } else if (param.getMaxPrice() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(accomodationJoin.get("price"),
                            param.getMaxPrice()));
                }

                // Lọc theo diện tích
                if (param.getMinAcreage() != null && param.getMaxAcreage() != null) {
                    predicates.add(criteriaBuilder.between(accomodationJoin.get("acreage"),
                            param.getMinAcreage(), param.getMaxAcreage()));
                } else if (param.getMinAcreage() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(accomodationJoin.get("acreage"),
                            param.getMinAcreage()));
                } else if (param.getMaxAcreage() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(accomodationJoin.get("acreage"),
                            param.getMaxAcreage()));
                }

                // Lọc theo các thuộc tính boolean
                if (param.getInterior() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("interior"), param.getInterior()));
                }
                if (param.getKitchen() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("kitchen"), param.getKitchen()));
                }
                if (param.getAirConditioner() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("airConditioner"), param.getAirConditioner()));
                }
                if (param.getHeater() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("heater"), param.getHeater()));
                }
                if (param.getInternet() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("internet"), param.getInternet()));
                }
                if (param.getOwner() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("owner"), param.getOwner()));
                }
                if (param.getParking() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("parking"), param.getParking()));
                }
                if (param.getToilet() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("toilet"), param.getToilet()));
                }
                if (param.getTime() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("time"), param.getTime()));
                }
                if (param.getSecurity() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("security"), param.getSecurity()));
                }
                if (param.getGender() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("gender"), param.getGender()));
                }

                // Lọc theo districtName
                if (param.getDistrictName() != null && !param.getDistrictName().isEmpty()) {
                    Join<Accomodation, District> districtJoin = accomodationJoin.join("district", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(districtJoin.get("name"), param.getDistrictName()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
