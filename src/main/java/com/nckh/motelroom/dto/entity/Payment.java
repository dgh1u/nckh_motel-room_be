package com.nckh.motelroom.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int code;
    private String desc;
    private boolean success;
    private Long orderCode;
    private Integer amount;
    private String description;
    private String descStatus;
    private String accountNumber;
    private String reference;
    private String transactionDateTime;
    private String currency;
    private String paymentLinkId;
    private String counterAccountBankId;
    private String counterAccountBankName;
    private String counterAccountName;
    private String counterAccountNumber;
    private String virtualAccountName;
    private String virtualAccountNumber;
    private String signature;
    private String email;
}
