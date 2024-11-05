package com.momo.dto.response;

import com.momo.entity.OrderDetail;
import com.momo.entity.Product;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OrdersResponse implements Serializable {
    private List<OrderDetailResponse> orderDetails;
    private Long remainMoney;
    private List<String> freeProducts;

    public OrdersResponse(List<OrderDetailResponse> orderDetails, Long remainMoney, List<String> freeProducts) {
        this.orderDetails = orderDetails;
        this.remainMoney = remainMoney;
        this.freeProducts = freeProducts;
    }
}
