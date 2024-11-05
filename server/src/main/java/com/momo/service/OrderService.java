package com.momo.service;

import com.momo.dto.request.OrderRequestDTO;
import com.momo.dto.response.OrdersResponse;

public interface OrderService {

    OrdersResponse createOrder(OrderRequestDTO orderRequest);
}
