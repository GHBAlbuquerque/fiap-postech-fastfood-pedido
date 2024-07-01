package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.CheckoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckoutGatewayImplTest {

    @InjectMocks
    CheckoutGatewayImpl checkoutGateway;

    @Mock
    CheckoutRepository checkoutRepository;

    @Test
    void saveTest() {

    }

    @Test
    void findAllTest() {

    }

}
