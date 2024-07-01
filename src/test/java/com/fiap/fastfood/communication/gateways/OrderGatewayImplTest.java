package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderGatewayImplTest {

    @InjectMocks
    OrderGatewayImpl orderGateway;

    @Mock
    OrderRepository orderRepository;

    @Test
    void saveOrderTest() {

    }

    @Test
    void getOrderByIdTest() {

    }

    @Test
    void listOrderTest() {

    }

}
