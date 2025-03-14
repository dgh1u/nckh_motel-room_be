package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Long> , JpaSpecificationExecutor<Accomodation> {

}
