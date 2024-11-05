package com.momo.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String date;

    private Long promotionAmount;

    public Promotion() {
    }

    public Promotion(String date, Long promotionAmount) {
        this.date = date;
        this.promotionAmount = promotionAmount;
    }

    public Long getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Long promotionAmount) {
        this.promotionAmount = promotionAmount;
    }
}
