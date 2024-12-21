package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Criteria;
import com.nckh.motelroom.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaRepository extends CrudRepository<Criteria, Integer> {
    List<Criteria> findAllByAcreageStartLessThanEqualAndAcreageEndGreaterThanEqualAndPriceStartLessThanEqualAndPriceEndGreaterThanEqualAndDistrict_IdAndMotelAndStopAndUserNot(
            double acreageStart, double acreageEnd, double priceStart, double priceEnd, long idDistrict, boolean motel, boolean stop, User user);

    Page<Criteria> findAllByUser(User user, Pageable pageable);

    Page<Criteria> findALlByUserAndStop(User user, boolean stop, Pageable pageable);
}
