package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.core.entity.Order;

import java.util.List;

public interface OrderUseCase {

    void createOrder(Order order, OrderGateway orderGateway) throws NoSuchEntityException, OrderCreationException;

    List<Order> listOrder(OrderGateway orderGateway);

    Order getOrderById(String id, OrderGateway orderGateway) throws EntityNotFoundException;

    Boolean validateOrderCustomer(Long customerId) throws NoSuchEntityException;

}
