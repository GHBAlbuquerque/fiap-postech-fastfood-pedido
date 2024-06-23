package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.response.GetOrderResponse;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;
import com.fiap.fastfood.external.orm.OrderORM;

import java.util.stream.Collectors;

public class OrderBuilder {

    public static GetOrderResponse fromDomainToResponse(Order order) {
        return GetOrderResponse.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalValue(order.getTotalValue())
                .items(order.getItems())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }

    public static Order fromRequestToDomain(CreateOrderRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .items(
                        request.getItems()
                                .stream()
                                .map(ItemBuilder::fromRequestToDomain)
                                .toList()
                )
                .build();
    }

    public static Order fromOrmToDomain(OrderORM orm) {
        return Order.builder()
                .id(orm.getId())
                .createdAt(orm.getCreatedAt())
                .updatedAt(orm.getUpdatedAt())
                .totalValue(orm.getTotalValue())
                .status(OrderStatus.valueOf(orm.getStatus()))
                .paymentStatus(OrderPaymentStatus.valueOf(orm.getPaymentStatus()))
                .items(
                        orm.getItems().stream().map(ItemBuilder::fromOrmToDomain).collect(Collectors.toList())
                )
                .build();
    }

    public static OrderORM fromDomainToOrm(Order order) {
        return OrderORM.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalValue(order.getTotalValue())
                .status(order.getStatus().toString())
                .paymentStatus(order.getPaymentStatus().toString())
                .items(
                        order.getItems().stream().map(ItemBuilder::fromDomainToOrm).collect(Collectors.toList())
                )
                .build();
    }
}
