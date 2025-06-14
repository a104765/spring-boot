package com.example.spring_boot.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class cardCreate {
    @NotBlank(message = "Card holder name must not be blank")
    private String cardholderName;

    @NotNull(message = "Initial Card Balance must be provided")
    @Min(value = 0, message = "Card Balance must not be negative, null or empty")
    @Column(precision = 10, scale = 2)
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
