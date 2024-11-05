package com.momo.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderDetailRequestDTO implements Serializable {
    Long productId;
    Long price;
    Integer quantity;
}
