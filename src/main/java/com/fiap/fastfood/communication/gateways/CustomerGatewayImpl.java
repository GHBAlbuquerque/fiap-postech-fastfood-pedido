package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.CustomerBuilder;
import com.fiap.fastfood.common.builders.ProductBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerGatewayImpl implements CustomerGateway {

    @Autowired
    private CustomerHTTPClient customerHTTPClient;

    private static final String CONTENT_TYPE = "application/json";

    @Override
    public Customer getCustomerByCpf(String cpf) throws EntityNotFoundException {
        final var result = customerHTTPClient.getCustomerByCpf(cpf, CONTENT_TYPE);
        final var response = result.getBody();

        if (response == null) {
            throw new EntityNotFoundException(
                    "ORDER-04",
                    String.format("Customer with CPF %s on Order Item not found", cpf)
            );
        }

        return CustomerBuilder.fromResponseToDomain(response);
    }
}
