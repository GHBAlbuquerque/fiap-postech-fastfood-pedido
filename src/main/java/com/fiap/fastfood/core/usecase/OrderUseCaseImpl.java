package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;

import java.util.List;

public class OrderUseCaseImpl implements OrderUseCase {

    @Override
    public Order createOrder(Order order, OrderGateway orderGateway) throws OrderCreationException {

        try {
            validateOrderCustomer(order.getCustomerId());

            order.setTotalValue(order.getTotalValue());
            order.setStatus(OrderStatus.RECEIVED);
            order.setPaymentStatus(OrderPaymentStatus.PENDING);
            orderGateway.saveOrder(order);

            return order;

        } catch (Exception ex) {
            throw new OrderCreationException(
                    "ORDER-02",
                    String.format("Couldn't create order. Error: %s", ex.getMessage())
            );
        }
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
    public Boolean validateOrderCustomer(Long customerId) throws NoSuchEntityException {
        return Boolean.TRUE;

        /*TODO: implementar a verificação de cliente via CustomerGateway com feign
        NoSuchEntityException(code: 0): The product that was requested doesn't exist. Verify the product and try again.
         */
    }


}
