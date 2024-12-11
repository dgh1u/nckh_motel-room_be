package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findDistrictById(Long id);
}
