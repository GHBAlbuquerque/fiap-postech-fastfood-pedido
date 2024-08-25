package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.response.CreatedOrderResponse;
import com.fiap.fastfood.common.dto.response.GetOrderResponse;
import com.fiap.fastfood.common.utils.TimeConverter;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;
import com.fiap.fastfood.external.orm.OrderORM;

import java.util.stream.Collectors;

public class OrderBuilder {

    public static GetOrderResponse fromDomainToGetResponse(Order order) {
        return GetOrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalValue(order.getTotalValue())
                .items(order.getItems())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }

    public static CreatedOrderResponse fromDomainToCreatedResponse(Order order) {
        return CreatedOrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .totalValue(order.getTotalValue())
                .items(
                        order.getItems()
                                .stream()
                                .map(ItemBuilder::fromDomainToResponse)
                                .toList()
                )
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

    public static Order fromCommandToDomain(CreateOrderCommand command) {
        return Order.builder()
                .customerId(command.getCustomerId())
                .items(command.getItems())
                .build();
    }

    public static Order fromOrmToDomain(OrderORM orm) {
        final var order = Order.builder()
                .id(orm.getId())
                .customerId(orm.getCustomerId())
                .totalValue(orm.getTotalValue())
                .status(OrderStatus.valueOf(orm.getStatus()))
                .paymentStatus(OrderPaymentStatus.valueOf(orm.getPaymentStatus()))
                .items(
                        orm.getItems().stream().map(ItemBuilder::fromOrmToDomain).collect(Collectors.toList())
                )
                .build();


        if (orm.getCreatedAt() != null)
            order.setCreatedAt(TimeConverter.convertToLocalDateTimeViaInstant(orm.getCreatedAt()));

        if (orm.getUpdatedAt() != null)
            order.setUpdatedAt(TimeConverter.convertToLocalDateTimeViaInstant(orm.getUpdatedAt()));

        return order;

    }

    public static OrderORM fromDomainToOrm(Order order) {
        var orderORM = OrderORM.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .totalValue(order.getTotalValue())
                .status(order.getStatus().toString())
                .paymentStatus(order.getPaymentStatus().toString())
                .items(
                        order.getItems().stream().map(ItemBuilder::fromDomainToOrm).collect(Collectors.toList())
                )
                .build();


        if (order.getCreatedAt() != null)
            orderORM.setCreatedAt(TimeConverter.convertToDateViaInstant(order.getCreatedAt()));

        if (order.getUpdatedAt() != null)
            orderORM.setUpdatedAt(TimeConverter.convertToDateViaInstant(order.getUpdatedAt()));

        return orderORM;
    }
}
