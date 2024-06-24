package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Order;

import java.util.List;

public interface ProductGateway {

    List<Order> listOrder();

    void saveOrder(Order order);

    Order getOrderById(String id) throws EntityNotFoundException;

}
