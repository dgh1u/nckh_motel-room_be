package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    Page<Action> findAllByUser_Id(Long id, Pageable pageable);
}