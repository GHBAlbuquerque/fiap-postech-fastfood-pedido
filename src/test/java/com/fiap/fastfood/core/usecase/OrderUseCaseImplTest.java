package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseImplTest {

    @InjectMocks
    private OrderUseCaseImpl orderUseCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private OrquestrationGateway orquestrationGateway;

    @Test
    void testCreateOrderSuccess() throws EntityNotFoundException {
        final var orderMock = createOrder();
        final var productMock = mock(Product.class);

        Mockito.when(orderGateway.saveOrder(orderMock))
                .thenReturn(orderMock);

        Mockito.when(productGateway.getProductByIdAndType(anyString(), anyString()))
                .thenReturn(productMock);

        Mockito.when(productGateway.validateProductValue(BigDecimal.ONE, productMock))
                .thenReturn(Boolean.TRUE);

        final var result =
                Assertions.assertDoesNotThrow(
                        () -> orderUseCase.createOrder(orderMock,
                                orderGateway,
                                productGateway,
                                customerGateway,
                                orquestrationGateway)
                );

        assertNotNull(result);
    }

    @Test
    void testCreateOrderError() throws EntityNotFoundException {
        final var orderMock = createOrder();
        final var productMock = mock(Product.class);

        TransactionInformationStorage.putReceiveCount("1");

        Mockito.when(productGateway.getProductByIdAndType(anyString(), anyString()))
                .thenReturn(productMock);

        Mockito.when(productGateway.validateProductValue(BigDecimal.ONE, productMock))
                .thenReturn(Boolean.FALSE);

        Assertions.assertThrows(
                OrderCreationException.class,
                () -> orderUseCase.createOrder(orderMock,
                        orderGateway,
                        productGateway,
                        customerGateway,
                        orquestrationGateway)
        );
    }

    @Test
    void testPrepareOrderSuccess() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");

        // Act
        Order result = orderUseCase.prepareOrder(order, orderGateway, orquestrationGateway);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
        verify(orderGateway).updateOrderPaymentStatus(order.getId(), OrderPaymentStatus.APPROVED);
        verify(orderGateway).updateOrderStatus(order.getId(), OrderStatus.IN_PREPARATION);
        verify(orquestrationGateway).sendResponse(order, OrquestrationStepEnum.PREPARE_ORDER, Boolean.TRUE);
    }

    @Test
    void testPrepareOrderFailure() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");
        TransactionInformationStorage.putReceiveCount("1");

        doThrow(new RuntimeException("Simulated exception")).when(orderGateway).updateOrderStatus(anyString(), any());

        // Act & Assert
        Assertions.assertThrows(
                OrderCreationException.class,
                () -> orderUseCase.prepareOrder(order,
                        orderGateway,
                        orquestrationGateway)
        );
    }


    @Test
    void testCompleteOrderSuccess() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");

        // Act
        Order result = orderUseCase.completeOrder(order, orderGateway, orquestrationGateway);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
        verify(orderGateway).updateOrderStatus(order.getId(), OrderStatus.COMPLETED);
        verify(orquestrationGateway).sendResponse(order, OrquestrationStepEnum.COMPLETE_ORDER, Boolean.TRUE);
    }

    @Test
    void testCompleteOrderFailure() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");
        TransactionInformationStorage.putReceiveCount("1");

        doThrow(new RuntimeException("Simulated exception")).when(orderGateway).updateOrderStatus(anyString(), any());

        // Act & Assert
        Assertions.assertThrows(
                OrderCreationException.class,
                () -> orderUseCase.completeOrder(order,
                        orderGateway,
                        orquestrationGateway)
        );
    }

    @Test
    void testCancelOrderSuccess() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");

        // Act
        Order result = orderUseCase.cancelOrder(order, orderGateway, orquestrationGateway);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
        verify(orderGateway).updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
        verify(orderGateway).updateOrderPaymentStatus(order.getId(), OrderPaymentStatus.REJECTED);
        verify(orquestrationGateway).sendResponse(order, OrquestrationStepEnum.CANCEL_ORDER, Boolean.TRUE);
    }

    @Test
    void testCancelOrderFailure() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("123");
        TransactionInformationStorage.putReceiveCount("1");

        doThrow(new RuntimeException("Simulated exception")).when(orderGateway).updateOrderStatus(anyString(), any());

        // Act & Assert
        Assertions.assertThrows(
                OrderCancellationException.class,
                () -> orderUseCase.cancelOrder(order,
                        orderGateway,
                        orquestrationGateway)
        );
    }


    @Test
    void listOrderTest() {
        final var gatewayMock = mock(OrderGateway.class);
        final var orderMock = createOrder();


        Mockito.when(gatewayMock.listOrder())
                .thenReturn(List.of(orderMock));

        final var result = orderUseCase.listOrder(gatewayMock);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getOrderByIdSuccessTest() throws EntityNotFoundException {
        final var gatewayMock = mock(OrderGateway.class);
        final var orderMock = createOrder();


        Mockito.when(gatewayMock.getOrderById(orderMock.getId()))
                .thenReturn(orderMock);

        final var result = orderUseCase.getOrderById(orderMock.getId(), gatewayMock);

        assertNotNull(result);
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
