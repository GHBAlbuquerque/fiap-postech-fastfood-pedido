package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.CheckoutBuilder;
import com.fiap.fastfood.common.interfaces.datasources.CheckoutRepository;
import com.fiap.fastfood.common.interfaces.gateways.CheckoutGateway;
import com.fiap.fastfood.core.entity.Checkout;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class CheckoutGatewayImpl implements CheckoutGateway {

    private final CheckoutRepository checkoutRepository;

    public CheckoutGatewayImpl(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Override
    public void save(Checkout checkout) {
        final var orm = CheckoutBuilder.fromDomainToOrm(checkout);
        checkoutRepository.save(orm);
    }

    @Override
    public List<Checkout> findAll() {
        final var orms = checkoutRepository.findAllByOrderByCreatedAtAsc();
        final var checkouts = orms.stream().map(orm -> CheckoutBuilder.fromOrmToDomain(orm)).collect(Collectors.toList());
        return checkouts;
    }
}
