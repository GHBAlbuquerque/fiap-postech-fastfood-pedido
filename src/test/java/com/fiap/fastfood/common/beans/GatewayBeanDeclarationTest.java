package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void OrderGatewayTest() {
        final var mock = Mockito.mock(OrderRepository.class);

        final var result = declaration.orderGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void CustomerGatewayTest() {
        final var mock = Mockito.mock(CustomerHTTPClient.class);

        final var result = declaration.customerGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void ProducttGatewayTest() {
        final var mock = Mockito.mock(ProductHTTPClient.class);

        final var result = declaration.productGateway(mock);

        Assertions.assertNotNull(result);
    }


}
