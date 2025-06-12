package com.example.spring_boot.dto;

import java.math.BigDecimal;

public class cardFundsChange {

    private Long id;
    private BigDecimal remainingBalance;


    public cardFundsChange(Long id, BigDecimal remainingBalance) {
        this.id = id;
        this.remainingBalance = remainingBalance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}