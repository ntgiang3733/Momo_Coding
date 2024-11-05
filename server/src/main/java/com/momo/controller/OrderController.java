package com.momo.controller;

import com.momo.dto.request.OrderRequestDTO;
import com.momo.dto.response.OrdersResponse;
import com.momo.dto.response.ResponseData;
import com.momo.dto.response.ResponseError;
import com.momo.entity.Orders;
import com.momo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseData<OrdersResponse> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "createOrder sc", orderService.createOrder(orderRequestDTO));
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
