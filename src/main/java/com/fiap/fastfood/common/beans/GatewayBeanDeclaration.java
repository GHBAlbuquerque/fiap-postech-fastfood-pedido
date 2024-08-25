package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.communication.gateways.CustomerGatewayImpl;
import com.fiap.fastfood.communication.gateways.OrderGatewayImpl;
import com.fiap.fastfood.communication.gateways.OrquestrationGatewayImpl;
import com.fiap.fastfood.communication.gateways.ProductGatewayImpl;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public OrderGateway orderGateway(OrderRepository repository) {
        return new OrderGatewayImpl(repository);
    }

    @Bean
    public ProductGateway productGateway(ProductHTTPClient client) {
        return new ProductGatewayImpl(client);
    }

    @Bean
    public CustomerGateway customerGateway(CustomerHTTPClient client) {
        return new CustomerGatewayImpl(client);
    }

    @Bean
    public OrquestrationGateway orquestrationGateway(OrderUseCase orderUseCase, OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway, MessageSender messageSender) {
        return new OrquestrationGatewayImpl(orderUseCase, orderGateway, productGateway, customerGateway, messageSender);
    }

}
