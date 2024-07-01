package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerGatewayImplTest {

    @InjectMocks
    private CustomerGatewayImpl customerGateway;

    @Mock
    private CustomerHTTPClient customerHTTPClient;

    @Test
    void getCustomerByIdTest() {

    }
}
