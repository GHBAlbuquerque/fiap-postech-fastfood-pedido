package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.CustomerBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;

public class CustomerGatewayImpl implements CustomerGateway {

    private CustomerHTTPClient customerHTTPClient;

    private static final String CONTENT_TYPE = "application/json";
    private static final String MICROSSERVICE = "ms_cliente";

    public CustomerGatewayImpl(CustomerHTTPClient customerHTTPClient) {
        this.customerHTTPClient = customerHTTPClient;
    }

    @Override
    public Customer getCustomerById(Long customerId) throws EntityNotFoundException {
        final var result = customerHTTPClient.getCustomerById(customerId, MICROSSERVICE, CONTENT_TYPE);
        final var response = result.getBody();

        if (response == null) {
            throw new EntityNotFoundException(
                    "ORDER-04",
                    String.format("Customer with id %s on Order Item not found", customerId)
            );
        }

        return CustomerBuilder.fromResponseToDomain(response);
    }
}
