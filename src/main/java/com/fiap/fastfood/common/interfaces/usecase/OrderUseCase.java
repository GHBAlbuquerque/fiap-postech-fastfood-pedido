package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.core.entity.Order;

import java.util.List;

public interface OrderUseCase {

    Order createOrder(Order order,
                      OrderGateway orderGateway,
                      ProductGateway productGateway,
                      CustomerGateway customerGateway,
                      OrquestrationGateway orquestrationGateway) throws NoSuchEntityException, OrderCreationException;

    Order prepareOrder(Order order,
                       OrderGateway orderGateway,
                       OrquestrationGateway orquestrationGateway) throws OrderCreationException, InterruptedException, EntityNotFoundException;

    Order completeOrder(Order order,
                        OrderGateway orderGateway,
                        OrquestrationGateway orquestrationGateway) throws OrderCreationException;

    Order cancelOrder(Order order,
                      OrderGateway orderGateway,
                      OrquestrationGateway orquestrationGateway) throws OrderCreationException, OrderCancellationException;

    List<Order> listOrder(OrderGateway orderGateway);

    Order getOrderById(String id, OrderGateway orderGateway) throws EntityNotFoundException;

    void validateOrderCustomer(Long customerId, CustomerGateway customerGateway) throws EntityNotFoundException;

    void validateOrderItens(List<Item> items, ProductGateway productGateway) throws EntityNotFoundException, OrderCreationException;

}
