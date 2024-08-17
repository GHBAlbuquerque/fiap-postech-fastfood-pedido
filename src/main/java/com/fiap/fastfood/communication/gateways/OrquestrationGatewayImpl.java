package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.common.logging.Constants;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;

import static com.fiap.fastfood.common.logging.Constants.*;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final OrderUseCase orderUseCase;
    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final CustomerGateway customerGateway;
    private final MessageSender messageSender;

    private static final Logger logger = LogManager.getLogger(OrquestrationGatewayImpl.class);

    public OrquestrationGatewayImpl(OrderUseCase orderUseCase, OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway, MessageSender messageSender) {
        this.orderUseCase = orderUseCase;
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
        this.customerGateway = customerGateway;
        this.messageSender = messageSender;
    }

    @Value("${aws.queue_resposta_criar_pedido.url}")
    private String queueResponseSaga;

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_criar_pedido.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
    public void listenToOrderCreation(MessageHeaders headers,
                                      CustomQueueMessage<CreateOrderCommand> message) throws OrderCreationException {
        logger.info(
                message.getHeaders().getSagaId(),
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var order = OrderBuilder.fromCommandToDomain(message.getBody());

            orderUseCase.createOrder(order,
                    orderGateway,
                    productGateway,
                    customerGateway,
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.ORDER_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_preparar_pedido.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
    public void listenToOrderPreparation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCreationException {
        logger.info(
                message.getHeaders().getSagaId(),
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getMicrosservice()
        );

        try {

            /*final var order = OrderBuilder.fromCommandToDomain(message.getBody());

            orderUseCase.createOrder(order,
                    orderGateway,
                    productGateway,
                    customerGateway);*/

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.ORDER_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_encerrar_pedido.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
    public void listenToOrderCompletion(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCreationException {

    }

    @Override
    @SqsListener(queueNames = "${aws.comando_cancelar_pedido.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
    public void listenToOrderCancellation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws OrderCancellationException {

    }

    @Override
    public void sendResponse(Order order,
                             OrquestrationStepEnum orquestrationStepEnum,
                             Boolean stepSuccessful) throws OrderCreationException {
        logger.info(
                LoggingPattern.RESPONSE_INIT_LOG,
                TransactionInformationStorage.getSagaId(),
                Constants.MS_SAGA
        );

        final var message = createResponseMessage(order, orquestrationStepEnum, stepSuccessful);

        try {

            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueResponseSaga
            );

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Preparation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    Constants.MS_SAGA,
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.ORDER_08_RESPONSE_SAGA, ex.getMessage());

        }
    }

    private static CustomQueueMessage<CreateOrderResponse> createResponseMessage(Order order, OrquestrationStepEnum orquestrationStepEnum, Boolean stepSuccessful) {
        final var headers = new CustomMessageHeaders(TransactionInformationStorage.getSagaId(), order.getId(), MESSAGE_TYPE_RESPONSE, MS_SAGA);
        return new CustomQueueMessage<>(
                headers,
                new CreateOrderResponse(order.getId(),
                        order.getCustomerId(),
                        TransactionInformationStorage.getPaymentId(),
                        orquestrationStepEnum,
                        stepSuccessful)
        );
    }
}
