package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.core.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class OrderUseCaseImplTest {

    @InjectMocks
    private OrderUseCaseImpl orderUseCase;

    @Test
    void createOrderTest() throws EntityNotFoundException {
        final var gatewayMock = Mockito.mock(OrderGateway.class);
        final var productGatewayMock = Mockito.mock(ProductGateway.class);
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);
        final var orderMock = createOrder();
        final var productMock = Mockito.mock(Product.class);

        Mockito.when(gatewayMock.saveOrder(orderMock))
                .thenReturn(orderMock);

        Mockito.when(productGatewayMock.getProductByIdAndType(anyString(), anyString()))
                .thenReturn(productMock);

        Mockito.when(productGatewayMock.validateProductValue(BigDecimal.ONE, productMock))
                .thenReturn(Boolean.TRUE);

        final var result =
                Assertions.assertDoesNotThrow(
                        () -> orderUseCase.createOrder(orderMock,
                                gatewayMock,
                                productGatewayMock,
                                customerGatewayMock)
                );

        Assertions.assertNotNull(result);
    }

    @Test
    void createOrderErrorTest() throws EntityNotFoundException {
        final var gatewayMock = Mockito.mock(OrderGateway.class);
        final var productGatewayMock = Mockito.mock(ProductGateway.class);
        final var customerGatewayMock = Mockito.mock(CustomerGateway.class);
        final var orderMock = createOrder();
        final var productMock = Mockito.mock(Product.class);

        Mockito.when(productGatewayMock.getProductByIdAndType(anyString(), anyString()))
                .thenReturn(productMock);

        Mockito.when(productGatewayMock.validateProductValue(BigDecimal.ONE, productMock))
                .thenReturn(Boolean.FALSE);

        Assertions.assertThrows(
                OrderCreationException.class,
                () -> orderUseCase.createOrder(orderMock,
                        gatewayMock,
                        productGatewayMock,
                        customerGatewayMock)
        );
    }

    @Test
    void listOrderTest() {
        final var gatewayMock = Mockito.mock(OrderGateway.class);
        final var orderMock = createOrder();


        Mockito.when(gatewayMock.listOrder())
                .thenReturn(List.of(orderMock));

        final var result = orderUseCase.listOrder(gatewayMock);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getOrderByIdTest() throws EntityNotFoundException {
        final var gatewayMock = Mockito.mock(OrderGateway.class);
        final var orderMock = createOrder();


        Mockito.when(gatewayMock.getOrderById(orderMock.getId()))
                .thenReturn(orderMock);

        final var result = orderUseCase.getOrderById(orderMock.getId(), gatewayMock);

        Assertions.assertNotNull(result);
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
