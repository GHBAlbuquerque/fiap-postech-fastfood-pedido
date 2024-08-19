package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.core.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageHeaders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.ORDER_07_COMMAND_PROCESSING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestrationGatewayImplTest {

    @InjectMocks
    private OrquestrationGatewayImpl orquestrationGateway;

    private OrderUseCase orderUseCase;
    private OrderGateway orderGateway;
    private ProductGateway productGateway;
    private CustomerGateway customerGateway;
    private MessageSender messageSender;

    @BeforeEach
    void setUp() {
        orderUseCase = mock(OrderUseCase.class);
        orderGateway = mock(OrderGateway.class);
        productGateway = mock(ProductGateway.class);
        customerGateway = mock(CustomerGateway.class);
        messageSender = mock(MessageSender.class);
    }

    @Test
    void testListenToOrderCreationSuccess() throws Exception {
        // Arrange
        final var command = createOrderCommand();
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        final var order = createOrder();
        when(OrderBuilder.fromCommandToDomain(command)).thenReturn(order);
        doNothing().when(orderUseCase).createOrder(any(), any(), any(), any(), any());

        // Act
        orquestrationGateway.listenToOrderCreation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).createOrder(eq(order), any(), any(), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderCreationFailure() throws Exception {
        // Arrange
        final var command = createOrderCommand();
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        final var order = new Order();
        when(OrderBuilder.fromCommandToDomain(command)).thenReturn(order);
        doThrow(new OrderCreationException(ORDER_07_COMMAND_PROCESSING, "Error")).when(orderUseCase).createOrder(any(), any(), any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCreationException.class, () ->
                orquestrationGateway.listenToOrderCreation(new MessageHeaders(Map.of()), message)
        );
        assertEquals(ORDER_07_COMMAND_PROCESSING, exception.getCode());
        assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    void testListenToOrderPreparationSuccess() throws Exception {
        // Arrange
        final var command = orderCommand();
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        Order order = new Order();
        when(orderGateway.getOrderById("orderId")).thenReturn(order);
        doNothing().when(orderUseCase).prepareOrder(any(), any(), any());

        // Act
        orquestrationGateway.listenToOrderPreparation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).prepareOrder(eq(order), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderPreparationFailure() throws Exception {
        // Arrange
        final var command = orderCommand();
        command.setOrderId("orderId");
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        when(orderGateway.getOrderById("orderId")).thenThrow(new EntityNotFoundException(ExceptionCodes.ORDER_01_NOT_FOUND, "Order not found"));
        doThrow(new OrderCreationException(ORDER_07_COMMAND_PROCESSING, "Error")).when(orderUseCase).prepareOrder(any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCreationException.class, () ->
                orquestrationGateway.listenToOrderPreparation(new MessageHeaders(Map.of()), message)
        );
        assertEquals(ORDER_07_COMMAND_PROCESSING, exception.getCode());
        assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    void testListenToOrderCompletionSuccess() throws Exception {
        // Arrange
        final var command = orderCommand();
        command.setOrderId("orderId");
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        Order order = new Order();
        when(orderGateway.getOrderById("orderId")).thenReturn(order);
        doNothing().when(orderUseCase).completeOrder(any(), any(), any());

        // Act
        orquestrationGateway.listenToOrderCompletion(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).completeOrder(eq(order), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderCompletionFailure() throws Exception {
        // Arrange
        final var command = orderCommand();
        command.setOrderId("orderId");
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        when(orderGateway.getOrderById("orderId")).thenThrow(new EntityNotFoundException(ExceptionCodes.ORDER_01_NOT_FOUND, "Order not found"));
        doThrow(new OrderCreationException(ORDER_07_COMMAND_PROCESSING, "Error")).when(orderUseCase).completeOrder(any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCreationException.class, () ->
                orquestrationGateway.listenToOrderCompletion(new MessageHeaders(Map.of()), message)
        );
        assertEquals(ORDER_07_COMMAND_PROCESSING, exception.getCode());
        assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    void testListenToOrderCancellationSuccess() throws Exception {
        // Arrange
        final var command = orderCommand();
        command.setOrderId("orderId");
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        final var order = new Order();
        when(orderGateway.getOrderById("orderId")).thenReturn(order);
        doNothing().when(orderUseCase).cancelOrder(any(), any(), any());

        // Act
        orquestrationGateway.listenToOrderCancellation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).cancelOrder(eq(order), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderCancellationFailure() throws Exception {
        // Arrange
        final var command = orderCommand();
        command.setOrderId("orderId");
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        when(orderGateway.getOrderById("orderId")).thenThrow(new EntityNotFoundException(ExceptionCodes.ORDER_01_NOT_FOUND, "Order not found"));
        doThrow(new OrderCancellationException(ORDER_07_COMMAND_PROCESSING, "Error")).when(orderUseCase).cancelOrder(any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCancellationException.class, () ->
                orquestrationGateway.listenToOrderCancellation(new MessageHeaders(Map.of()), message)
        );
        assertEquals(ORDER_07_COMMAND_PROCESSING, exception.getCode());
        assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    void testSendResponseSuccess() throws Exception {
        // Arrange
        final var order = new Order("orderId", 1L);
        final var stepEnum = OrquestrationStepEnum.CREATE_ORDER;
        boolean stepSuccessful = true;
        final var response = new CreateOrderResponse("orderId", 1L, "paymentId", stepEnum, stepSuccessful);
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "response"),
                response
        );
        doNothing().when(messageSender).sendMessage(any(), any(), any());

        // Act
        orquestrationGateway.sendResponse(order, stepEnum, stepSuccessful);

        // Assert
        final var messageCaptor = ArgumentCaptor.forClass(CustomQueueMessage.class);
        verify(messageSender).sendMessage(messageCaptor.capture(), eq("sagaId"), any());
        final var capturedMessage = messageCaptor.getValue();
        assertEquals(response, capturedMessage.getBody());
    }

    @Test
    void testSendResponseFailure() throws Exception {
        // Arrange
        final var order = new Order("orderId", 1L);
        final var stepEnum = OrquestrationStepEnum.CREATE_ORDER;
        boolean stepSuccessful = true;
        final var response = new CreateOrderResponse("orderId", 1L, "paymentId", stepEnum, stepSuccessful);
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "response"),
                response
        );
        doThrow(new RuntimeException("Send failed")).when(messageSender).sendMessage(any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCreationException.class, () ->
                orquestrationGateway.sendResponse(order, stepEnum, stepSuccessful)
        );
        assertEquals(ExceptionCodes.ORDER_08_RESPONSE_SAGA, exception.getCode());
        assertTrue(exception.getMessage().contains("Send failed"));
    }

    private Order createOrder() {
        final var item = getItem();

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

    private static Item getItem() {
        final var item = new Item()
                .setProductId("productId")
                .setProductType("SANDWICH")
                .setItemValue(BigDecimal.ONE)
                .setQuantity(1);
        return item;
    }

    private CreateOrderCommand createOrderCommand() {
        return new CreateOrderCommand(1L, List.of(getItem()));
    }

    private OrderCommand orderCommand() {
        return new OrderCommand("orderId", 1L, "paymentId");
    }

}
