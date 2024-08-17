package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;

import java.util.List;

public interface OrderGateway {

    List<Order> listOrder();

    Order saveOrder(Order order);

    Order getOrderById(String id) throws EntityNotFoundException;

    Order updateOrderStatus(String id, OrderStatus orderStatus) throws EntityNotFoundException;

    Order updateOrderPaymentStatus(String id, OrderPaymentStatus orderPaymentStatus) throws EntityNotFoundException;

}
