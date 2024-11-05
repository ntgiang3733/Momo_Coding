package com.momo.service.impl;

import com.momo.dto.request.OrderDetailRequestDTO;
import com.momo.dto.request.OrderRequestDTO;
import com.momo.dto.response.OrderDetailResponse;
import com.momo.dto.response.OrdersResponse;
import com.momo.entity.Orders;
import com.momo.entity.OrderDetail;
import com.momo.entity.Product;
import com.momo.entity.Promotion;
import com.momo.repository.OrderDetailRepository;
import com.momo.repository.OrderRepository;
import com.momo.repository.ProductRepository;
import com.momo.repository.PromotionRepository;
import com.momo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final int COUNT_ORDER_TO_PROMOTION = 2;
    private final long MAX_BUDGET_PER_DAY = 50000L;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final PromotionRepository promotionRepository;

    @Transactional
    @Override
    public OrdersResponse createOrder(OrderRequestDTO orderRequest) {
        try {
            List<String> freeProducts = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            List<Orders> latestOrder = orderRepository.getLatestOrders(COUNT_ORDER_TO_PROMOTION);
            List<List<Long>> latestProductIdList = new ArrayList<>();
            for (Orders od: latestOrder) {
                List<Long> pdIds = orderDetailRepository.findProductIdsByOrderId(od.getId());
                latestProductIdList.add(pdIds);
            }
            //

            // Orders
            Orders order = new Orders();
            order.setCreatedAt(new Date());
            Long totalPrice = orderRequest.getOrderDetails().stream()
                    .mapToLong(detail -> detail.getPrice() * detail.getQuantity())
                    .sum();
            order.setTotalPrice(totalPrice);
            order.setRemainMoney(orderRequest.getTotalMoney() - totalPrice);

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (OrderDetailRequestDTO orderDetailRequestDTO: orderRequest.getOrderDetails()) {
                OrderDetail orderDetail = new OrderDetail();
                Product product = productRepository.findById(orderDetailRequestDTO.getProductId()).get();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(orderDetailRequestDTO.getQuantity());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);

                // check promotion
                boolean checkPromotion = true;
                String today = formatter.format(new Date());
                Optional<Promotion> todayPromotion = promotionRepository.findByDate(today);
                if (todayPromotion.isEmpty() || todayPromotion.get().getPromotionAmount() < MAX_BUDGET_PER_DAY) {
                    if (latestProductIdList.size() < 2) {
                        checkPromotion = false;
                    } else {
                        for (List<Long> productIdList: latestProductIdList) {
                            if (!productIdList.contains(product.getId())) {
                                checkPromotion = false;
                            }
                        }
                    }

                    if (checkPromotion) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        String yesterday =  formatter.format(calendar.getTime());
                        Optional<Promotion> yesterdayPromotion = promotionRepository.findByDate(yesterday);
                        Random random = new Random();
                        if (yesterdayPromotion.isPresent() && yesterdayPromotion.get().getPromotionAmount() == MAX_BUDGET_PER_DAY) {
                            checkPromotion = (random.nextInt(10) + 1) == 1;
                        } else {
                            checkPromotion = (random.nextInt(10) + 1) <= 5;
                        }
                    }

                    if (checkPromotion) {
                        freeProducts.add(product.getProductName());
                        if (todayPromotion.isPresent()) {
                            todayPromotion.get().setPromotionAmount(todayPromotion.get().getPromotionAmount() + product.getProductPrice());
                            promotionRepository.save(todayPromotion.get());
                        } else {
                            Promotion promotion = new Promotion(today, product.getProductPrice());
                            promotionRepository.save(promotion);
                        }
                    }
                }

            }
            order.setOrderDetails(orderDetails);
            OrdersResponse ordersResponse = new OrdersResponse(
                    order.getOrderDetails().stream()
                            .map(orderDetail -> new OrderDetailResponse(
                                    orderDetail.getProduct().getProductName(),
                                    orderDetail.getProduct().getProductPrice(),
                                    orderDetail.getProduct().getProductImage(),
                                    orderDetail.getQuantity()
                            ))
                            .collect(Collectors.toList()),
                    order.getRemainMoney(),
                    freeProducts
            );
            return ordersResponse;
        } catch (Exception e) {
            throw e;
        }


    }
}
