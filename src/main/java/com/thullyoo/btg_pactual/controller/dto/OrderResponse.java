package com.thullyoo.btg_pactual.controller.dto;

import com.thullyoo.btg_pactual.entity.Order;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {
    public static OrderResponse toOrderResponse(Order order){
        return new OrderResponse(order.getId(), order.getClientId(), order.getTotal());
    }
}
