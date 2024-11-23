package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "recharge")
public class Recharge {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "complete_at")
    private Instant completeAt;

}