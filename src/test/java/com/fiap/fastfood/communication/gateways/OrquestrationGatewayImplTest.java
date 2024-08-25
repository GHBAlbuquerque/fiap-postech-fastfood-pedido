package com.fiap.fastfood.communication.gateways;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.core.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Mock
    private OrderUseCase orderUseCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private MessageSender messageSender;

    @Test
    void testListenToOrderCreationSuccess() throws Exception {
        // Arrange
        final var command = createOrderCommand();
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );
        final var order = createOrder();
        when(orderUseCase.createOrder(any(), any(), any(), any(), any())).thenReturn(order);

        // Act
        orquestrationGateway.listenToOrderCreation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).createOrder(any(), any(), any(), any(), any());
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
        final var order = createOrder();
        when(orderUseCase.prepareOrder(any(), any(), any())).thenReturn(order);

        // Act
        orquestrationGateway.listenToOrderPreparation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).prepareOrder(any(), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderPreparationFailure() throws Exception {
        // Arrange
        final var command = orderCommand();

        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );

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

        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );

        final var order = createOrder();
        when(orderUseCase.completeOrder(any(), any(), any())).thenReturn(order);

        // Act
        orquestrationGateway.listenToOrderCompletion(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).completeOrder(any(), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderCompletionFailure() throws Exception {
        // Arrange
        final var command = orderCommand();

        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );

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

        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );

        final var order = createOrder();
        when(orderUseCase.cancelOrder(any(), any(), any())).thenReturn(order);

        // Act
        orquestrationGateway.listenToOrderCancellation(new MessageHeaders(Map.of()), message);

        // Assert
        verify(orderUseCase).cancelOrder(any(), any(), any());
        verify(messageSender, never()).sendMessage(any(), any(), any());
    }

    @Test
    void testListenToOrderCancellationFailure() throws Exception {
        // Arrange
        final var command = orderCommand();
        final var message = new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                command
        );

        doThrow(new OrderCancellationException(ORDER_07_COMMAND_PROCESSING, "Error")).when(orderUseCase).cancelOrder(any(), any(), any());

        // Act & Assert
        final var exception = assertThrows(OrderCancellationException.class, () ->
                orquestrationGateway.listenToOrderCancellation(new MessageHeaders(Map.of()), message)
        );

        assertEquals(ORDER_07_COMMAND_PROCESSING, exception.getCode());
    }

    @Test
    void testSendResponseSuccess() throws Exception {
        // Arrange
        final var order = createOrder();
        final var stepEnum = OrquestrationStepEnum.CREATE_ORDER;
        boolean stepSuccessful = true;

        when(messageSender.sendMessage(any(), any(), any())).thenReturn(new SendMessageResult());

        // Act
        orquestrationGateway.sendResponse(order, stepEnum, stepSuccessful);

        // Assert
        verify(messageSender).sendMessage(any(), any(), any());
    }

    @Test
    void testSendResponseFailure() throws Exception {
        // Arrange
        final var order = new Order("orderId", 1L);
        final var stepEnum = OrquestrationStepEnum.CREATE_ORDER;
        boolean stepSuccessful = true;

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
        return new Item()
                .setProductId("productId")
                .setProductType("SANDWICH")
                .setItemValue(BigDecimal.ONE)
                .setQuantity(1);
    }

    private CreateOrderCommand createOrderCommand() {
        return new CreateOrderCommand(1L, List.of(getItem()));
    }

    private OrderCommand orderCommand() {
        return new OrderCommand("orderId", 1L, "paymentId");
    }

}
