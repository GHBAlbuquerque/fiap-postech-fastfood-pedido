package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.springframework.messaging.MessageHeaders;

public interface OrquestrationGateway {

    void listenToOrderCreation(MessageHeaders headers, CustomQueueMessage<CreateOrderCommand> message) throws OrderCreationException;

    void listenToOrderPreparation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCreationException;

    void listenToOrderCompletion(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCreationException;

    void listenToOrderCancellation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCancellationException;

    void sendResponse(Order order,
                      OrquestrationStepEnum orquestrationStepEnum,
                      Boolean stepSuccessful) throws OrderCreationException;
}
