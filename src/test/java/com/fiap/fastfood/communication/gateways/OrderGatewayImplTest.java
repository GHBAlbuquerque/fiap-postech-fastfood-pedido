package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderGatewayImplTest {

    @InjectMocks
    OrderGatewayImpl orderGateway;

    @Mock
    OrderRepository repository;

    @Test
    void saveOrderTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        when(repository.save(any()))
                .thenReturn(orderORM);

        assertNotNull(
                orderGateway.saveOrder(orderMock)
        );
    }

    @Test
    void getOrderByIdTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        when(repository.findById(any()))
                .thenReturn(Optional.ofNullable(orderORM));

        final var result = Assertions.assertDoesNotThrow(
                () -> orderGateway.getOrderById("id")
        );

        assertNotNull(result);
    }

    @Test
    void testUpdateOrderStatus() throws EntityNotFoundException {
        // Arrange
        String id = "123";
        final var orderORM = OrderBuilder.fromDomainToOrm(createOrder());
        final var order = createOrder();

        order.setStatus(OrderStatus.READY);

        when(repository.findById(id)).thenReturn(Optional.of(orderORM));
        when(repository.save(any())).thenReturn(orderORM);

        // Act
        Order result = orderGateway.updateOrderStatus(id, OrderStatus.READY);

        // Assert
        assertNotNull(result);
        assertEquals(OrderStatus.CREATED, result.getStatus());
    }

    @Test
    void testUpdateOrderPaymentStatus() throws EntityNotFoundException {
        // Arrange
        String id = "123";
        final var orderORM = OrderBuilder.fromDomainToOrm(createOrder());
        final var order = createOrder();

        order.setPaymentStatus(OrderPaymentStatus.APPROVED);
        orderORM.setPaymentStatus(OrderPaymentStatus.APPROVED.name());

        when(repository.findById(id)).thenReturn(Optional.of(orderORM));
        when(repository.save(any())).thenReturn(orderORM);

        // Act
        Order result = orderGateway.updateOrderPaymentStatus(id, OrderPaymentStatus.APPROVED);

        // Assert
        assertNotNull(result);
        assertEquals(OrderPaymentStatus.APPROVED, result.getPaymentStatus());
    }

    @Test
    void listOrderTest() {
        final var orderMock = createOrder();
        final var orderORM = OrderBuilder.fromDomainToOrm(orderMock);

        when(repository.findAll())
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
                .status(OrderStatus.CREATED)
                .paymentStatus(OrderPaymentStatus.PENDING)
                .build();
    }
}
