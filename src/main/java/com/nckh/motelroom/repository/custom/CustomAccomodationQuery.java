package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.District;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.utils.CriteriaBuilderUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomAccomodationQuery {

    private CustomAccomodationQuery() {}

    @Data
    @NoArgsConstructor
    public static class AccomodationFilterParam {
        private String keywords;
        private Double minAcreage;
        private Double maxAcreage;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private String districtName;
        private Boolean interior;
        private Boolean kitchen;
        private Boolean airConditioner;
        private Boolean heater;
        private Boolean internet;
        private Boolean owner;
        private Boolean parking;
        private Boolean toilet;
        private Boolean time;
        private Boolean security;
        private Boolean gender;
    }

    public static Specification<Accomodation> getFilterAccomodation(AccomodationFilterParam param) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Accomodation, District> districtJoin = root.join("district");
            Join<Accomodation, Post> postJoin = root.join("post", JoinType.LEFT);

            // Lọc theo tiêu đề bài đăng
            if (param.getKeywords() != null) {
                predicates.add(CriteriaBuilderUtil.createPredicateForSearchInsensitive(
                        postJoin, criteriaBuilder, param.getKeywords(), "title"));
            }

            if (param.getDistrictName() != null && !param.getDistrictName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(districtJoin.get("name"), param.getDistrictName()));
            }

            // Lọc theo khoảng giá
            if (param.getMinPrice() != null && param.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), param.getMinPrice(), param.getMaxPrice()));
            } else if (param.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), param.getMinPrice()));
            } else if (param.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), param.getMaxPrice()));
            }

            // Lọc theo diện tích
            if (param.getMinAcreage() != null && param.getMaxAcreage() != null) {
                predicates.add(criteriaBuilder.between(root.get("acreage"), param.getMinAcreage(), param.getMaxAcreage()));
            } else if (param.getMinAcreage() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("acreage"), param.getMinAcreage()));
            } else if (param.getMaxAcreage() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("acreage"), param.getMaxAcreage()));
            }


            // Lọc theo khu vực
            if (param.getDistrictName() != null && !param.getDistrictName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(districtJoin.get("name"), param.getDistrictName()));
            }

            // Lọc theo đặc điểm boolean
            if (param.getInterior() != null) {
                predicates.add(criteriaBuilder.equal(root.get("interior"), param.getInterior()));
            }
            if (param.getKitchen() != null) {
                predicates.add(criteriaBuilder.equal(root.get("kitchen"), param.getKitchen()));
            }
            if (param.getAirConditioner() != null) {
                predicates.add(criteriaBuilder.equal(root.get("airConditioner"), param.getAirConditioner()));
            }
            if (param.getHeater() != null) {
                predicates.add(criteriaBuilder.equal(root.get("heater"), param.getHeater()));
            }
            if (param.getInternet() != null) {
                predicates.add(criteriaBuilder.equal(root.get("internet"), param.getInternet()));
            }
            if (param.getOwner() != null) {
                predicates.add(criteriaBuilder.equal(root.get("owner"), param.getOwner()));
            }
            if (param.getParking() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parking"), param.getParking()));
            }
            if (param.getToilet() != null) {
                System.out.println("Toilet field type: " + root.get("toilet").getJavaType());
                predicates.add(criteriaBuilder.equal(root.get("toilet"), param.getToilet()));
            }
            if (param.getTime() != null) {
                predicates.add(criteriaBuilder.equal(root.get("time"), param.getTime()));
            }
            if (param.getSecurity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("security"), param.getSecurity()));
            }
            if (param.getGender() != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), param.getGender()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}