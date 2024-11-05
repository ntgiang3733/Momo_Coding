package com.momo.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderDetailResponse implements Serializable {
    private String productName;
    private Long price;
    private String image;
    private Integer quantity;

    public OrderDetailResponse(String productName, Long price, String image, Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
