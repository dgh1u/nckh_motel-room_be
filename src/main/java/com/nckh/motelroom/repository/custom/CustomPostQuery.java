package com.nckh.motelroom.repository.custom;

import com.nckh.motelroom.constant.Constant;
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
        private Boolean del;
        private Long userId;
        private String sortField;
        private String sortType;
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
                predicates.add(criteriaBuilder.equal(root.get("notApproved"), param.getNotApproved()));
            }

            // Lọc theo trạng thái hiển thị del
            if (param.getDel() != null) {
                predicates.add(criteriaBuilder.equal(root.get("del"), param.getDel()));
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
                    param.getGender() != null || param.getMotel() != null ||
                    param.getMotels() != null || // Thêm kiểm tra cho motels
                    param.getOpenHours() != null || param.getSecondMotel() != null ||
                    param.getDelivery() != null || param.getDineIn() != null ||
                    param.getTakeAway() != null || param.getBigSpace() != null ||
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

                // Cập nhật xử lý motel - hỗ trợ cả hai phương thức
                if (param.getMotels() != null && !param.getMotels().isEmpty()) {
                    // Sử dụng IN khi có nhiều giá trị
                    predicates.add(accomodationJoin.get("motel").in(param.getMotels()));
                } else if (param.getMotel() != null) {
                    // Vẫn giữ phương thức cũ để đảm bảo tương thích ngược
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("motel"), param.getMotel()));
                }

                if (param.getOpenHours() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("openHours"), param.getOpenHours()));
                }

                if (param.getSecondMotel() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("secondMotel"), param.getSecondMotel()));
                }

                if (param.getDelivery() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("delivery"), param.getDelivery()));
                }

                if (param.getDineIn() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("dineIn"), param.getDineIn()));
                }

                if (param.getTakeAway() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("takeAway"), param.getTakeAway()));
                }

                if (param.getBigSpace() != null) {
                    predicates.add(criteriaBuilder.equal(accomodationJoin.get("bigSpace"), param.getBigSpace()));
                }

                // Lọc theo districtName
                if (param.getDistrictName() != null && !param.getDistrictName().isEmpty()) {
                    Join<Accomodation, District> districtJoin = accomodationJoin.join("district", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(districtJoin.get("name"), param.getDistrictName()));
                }
            }

            // Xử lý sắp xếp
            if (param.sortField != null && !param.sortField.equals("")) {
                if ("price".equals(param.sortField)) {
                    // Sắp xếp theo giá - cần join với Accomodation nếu chưa join
                    Join<Post, Accomodation> priceJoin = root.join("accomodation", JoinType.LEFT);

                    if (param.sortType.equals(Constant.SortType.DESC) || param.sortType.equals("")) {
                        // Giá cao trước (DESC)
                        query.orderBy(criteriaBuilder.desc(priceJoin.get("price")));
                    } else if (param.sortType.equals(Constant.SortType.ASC)) {
                        // Giá thấp trước (ASC)
                        query.orderBy(criteriaBuilder.asc(priceJoin.get("price")));
                    }
                } else {
                    // Sắp xếp theo các trường khác của Post
                    if (param.sortType.equals(Constant.SortType.DESC) || param.sortType.equals("")) {
                        query.orderBy(criteriaBuilder.desc(root.get(param.sortField)));
                    } else if (param.sortType.equals(Constant.SortType.ASC)) {
                        query.orderBy(criteriaBuilder.asc(root.get(param.sortField)));
                    }
                }
            } else {
                // Mặc định sắp xếp theo ID giảm dần (tin mới nhất trước)
                query.orderBy(criteriaBuilder.desc(root.get("id")));
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}