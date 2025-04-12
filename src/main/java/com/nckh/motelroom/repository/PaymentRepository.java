package com.nckh.motelroom.repository;

import com.nckh.motelroom.dto.entity.Payment;
import com.nckh.motelroom.model.PaymentHistory;
import com.nckh.motelroom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentHistory, Long>, JpaSpecificationExecutor<PaymentHistory> {

}
