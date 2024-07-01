package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderGatewayImpl implements OrderGateway {

    private final OrderRepository repository;

    public OrderGatewayImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order saveOrder(Order order) {
        final var orm = OrderBuilder.fromDomainToOrm(order);
        return OrderBuilder.fromOrmToDomain(repository.save(orm));
    }

    @Override
    public Order getOrderById(String id) throws EntityNotFoundException {
        return repository.findById(id)
                .map(OrderBuilder::fromOrmToDomain)
                .orElseThrow(() -> new EntityNotFoundException("ORDER-01", String.format("Order with ID %s not found", id)));
    }

    @Override
    public List<Order> listOrder() {
        return repository.findAll()
                .stream()
                .filter(
                        order -> OrderStatus.valueOf(order.getStatus()) != OrderStatus.COMPLETED
                )
                .map(OrderBuilder::fromOrmToDomain)
                .sorted(Comparator.comparing(order -> getOrderStatusPriority(order.getStatus())))
                .collect(Collectors.toList());
    }

    private int getOrderStatusPriority(OrderStatus status) {
        return switch (status) {
            case READY -> 1;
            case IN_PREPARATION -> 2;
            case RECEIVED -> 3;
            default -> 0;
        };
    }
}
