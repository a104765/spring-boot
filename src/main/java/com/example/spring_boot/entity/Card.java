package com.example.spring_boot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardholderName;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC"
    )
    private Instant createdAt;


    @JsonIgnore
    @Version
    private Long version;

    // Getters
    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public BigDecimal  getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }

    //Setters
    public void setBalance(BigDecimal  balance) {
        this.balance = balance;
    }



    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}

