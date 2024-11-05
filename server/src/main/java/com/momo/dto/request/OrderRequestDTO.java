package com.momo.dto.request;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OrderRequestDTO implements Serializable {
    private List<OrderDetailRequestDTO> orderDetails;
    private Long totalMoney;

    public OrderRequestDTO(List<OrderDetailRequestDTO> orderDetails, Long totalMoney) {
        this.orderDetails = orderDetails;
        this.totalMoney = totalMoney;
    }
}
