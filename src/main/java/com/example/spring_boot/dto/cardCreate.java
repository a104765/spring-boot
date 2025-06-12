package com.example.spring_boot.dto;

import java.math.BigDecimal;

public class cardCreate {
    private String cardholderName;
    private BigDecimal initialBalance;


    //Constructor
    public cardCreate(String cardholderName, BigDecimal initialBalance) {
        this.cardholderName = cardholderName;
        this.initialBalance = initialBalance;
    }


    //Getters
    public String getCardholderName() {
        return cardholderName;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    //Setters
    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }


}
