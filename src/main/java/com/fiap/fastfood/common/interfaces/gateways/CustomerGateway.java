package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Customer;

public interface CustomerGateway {

    Customer getCustomerById(Long id) throws EntityNotFoundException;
}
