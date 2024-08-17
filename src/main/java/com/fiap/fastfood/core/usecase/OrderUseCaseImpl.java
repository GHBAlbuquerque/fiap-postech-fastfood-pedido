package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.common.logging.Constants;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.fiap.fastfood.common.logging.Constants.MAX_RECEIVE_COUNT;

public class OrderUseCaseImpl implements OrderUseCase {

    private static final Logger logger = LogManager.getLogger(OrderUseCaseImpl.class);

    @Override
    public Order createOrder(Order order,
                             OrderGateway orderGateway,
                             ProductGateway productGateway,
                             CustomerGateway customerGateway,
                             OrquestrationGateway orquestrationGateway)
            throws OrderCreationException {

        logger.info(
                LoggingPattern.ORDER_CREATION_INIT_LOG,
                TransactionInformationStorage.getSagaId(),
                Constants.MS_SAGA
        );

        try {
            validateOrderCustomer(order.getCustomerId(), customerGateway);
            validateOrderItens(order.getItems(), productGateway);

            order.setTotalValue(order.getTotalValue());
            order.setStatus(OrderStatus.CREATED);
            order.setPaymentStatus(OrderPaymentStatus.PENDING);

            final var savedOrder = orderGateway.saveOrder(order);

            orquestrationGateway.sendResponse(
                    savedOrder,
                    OrquestrationStepEnum.CREATE_ORDER,
                    Boolean.TRUE
            );

            return savedOrder;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORDER_CREATION_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {
                orquestrationGateway.sendResponse(
                        order,
                        OrquestrationStepEnum.CREATE_ORDER,
                        Boolean.FALSE
                );
            }

            throw new OrderCreationException(
                    ExceptionCodes.ORDER_02_ORDER_CREATION,
                    String.format("Couldn't create order. Error: %s", ex.getMessage())
            );
        }
    }

    @Override
    public Order prepareOrder(Order order, OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public Order completeOrder(Order order, OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public Order cancelOrder(Order order, OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }


    @Override
    public List<Order> listOrder(OrderGateway orderGateway) {
        return orderGateway.listOrder();
    }

    @Override
    public Order getOrderById(String id, OrderGateway orderGateway) throws EntityNotFoundException {
        return orderGateway.getOrderById(id);
    }

    @Override
    public void validateOrderCustomer(Long customerId, CustomerGateway customerGateway) throws EntityNotFoundException {
        customerGateway.getCustomerById(customerId);
    }

    @Override
    public void validateOrderItens(List<Item> items, ProductGateway productGateway) throws EntityNotFoundException, OrderCreationException {
        for (Item item : items) {
            var product = productGateway.getProductByIdAndType(
                    item.getProductId(),
                    item.getProductType());

            var result = productGateway.validateProductValue(
                    item.getItemValue(),
                    product
            );

            if (!result)
                throw new OrderCreationException(
                        ExceptionCodes.ORDER_05_PRODUCT_PRICE_UNMATCH,
                        "Item price does not match product price."
                );
        }

    }


}
