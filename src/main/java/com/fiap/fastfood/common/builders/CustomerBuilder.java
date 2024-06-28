package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.services.customers.GetCustomerResponse;

public class CustomerBuilder {

    public static Customer fromResponseToDomain(GetCustomerResponse response) {

        return new Customer()
                .setId(response.getId())
                .setName(response.getName())
                .setBirthday(response.getBirthday())
                .setCpf(response.getCpf())
                .setEmail(response.getEmail());
    }
}
