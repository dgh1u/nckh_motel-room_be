package com.nckh.motelroom.repository;

import com.nckh.motelroom.model.Notification;
import com.nckh.motelroom.model.User;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findALlByUser(User user, Pageable pageable);

    Page<Notification> findDistinctByUser(User user, Pageable pageable);

    Page<Notification> findALlByUserAndCriteria_id(User user, Long criteriaId, Pageable pageable);
}
