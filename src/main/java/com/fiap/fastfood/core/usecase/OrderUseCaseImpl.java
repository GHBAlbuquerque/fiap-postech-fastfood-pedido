package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderPaymentStatus;
import com.fiap.fastfood.core.entity.OrderStatus;

import java.util.List;

public class OrderUseCaseImpl implements OrderUseCase {

    @Override
    public Order createOrder(Order order,
                             OrderGateway orderGateway,
                             ProductGateway productGateway,
                             CustomerGateway customerGateway)
            throws OrderCreationException {

        try {
            validateOrderCustomer(order.getCustomerId(), customerGateway);
            validateOrderItens(order.getItems(), productGateway);

            order.setTotalValue(order.getTotalValue());
            order.setStatus(OrderStatus.CREATED);
            order.setPaymentStatus(OrderPaymentStatus.PENDING);

            return orderGateway.saveOrder(order);

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
                        "ORDER-05",
                        String.format("Item price does not match product price.")
                );
        }

    }


}
