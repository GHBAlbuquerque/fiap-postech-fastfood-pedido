package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GatewayBeanDeclarationTest {

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
    void ProductGatewayTest() {
        final var mock = Mockito.mock(ProductHTTPClient.class);

        final var result = declaration.productGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void OrquestrationGatewayTest() {
        final var mock = Mockito.mock(OrderUseCase.class);
        final var mockGateway = Mockito.mock(OrderGateway.class);
        final var mockProductGateway = Mockito.mock(ProductGateway.class);
        final var mockCustomerGateway = Mockito.mock(CustomerGateway.class);
        final var mockSender = Mockito.mock(MessageSender.class);

        final var result = declaration.orquestrationGateway(mock, mockGateway, mockProductGateway, mockCustomerGateway, mockSender);

        Assertions.assertNotNull(result);
    }


}
