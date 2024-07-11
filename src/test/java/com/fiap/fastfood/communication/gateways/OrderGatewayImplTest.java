package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderGatewayImplTest {

    @InjectMocks
    OrderGatewayImpl orderGateway;

    @Mock
    OrderRepository orderRepository;

    @Test
    void saveOrderTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        Mockito.when(orderRepository.save(any()))
                .thenReturn(orderORM);

        Assertions.assertNotNull(
                orderGateway.saveOrder(orderMock)
        );
    }

    @Test
    void getOrderByIdTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        Mockito.when(orderRepository.findById(any()))
                .thenReturn(Optional.ofNullable(orderORM));

        final var result = Assertions.assertDoesNotThrow(
                () -> orderGateway.getOrderById("id")
        );

        Assertions.assertNotNull(result);
    }

    @Test
    void listOrderTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        Mockito.when(orderRepository.findAll())
                .thenReturn(List.of(orderORM));

        final var result = Assertions.assertDoesNotThrow(
                () -> orderGateway.listOrder()
        );

        Assertions.assertFalse(result.isEmpty());
    }

    private Order createOrder() {
        final var item = new Item()
                .setProductId("productId")
                .setProductType("SANDWICH")
                .setItemValue(BigDecimal.ONE)
                .setQuantity(1);

        return Order.builder()
                .id("orderId")
                .customerId(1L)
                .items(List.of(item))
                .totalValue(BigDecimal.ONE)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .status(OrderStatus.RECEIVED)
                .paymentStatus(OrderPaymentStatus.PENDING)
                .build();
    }
}
