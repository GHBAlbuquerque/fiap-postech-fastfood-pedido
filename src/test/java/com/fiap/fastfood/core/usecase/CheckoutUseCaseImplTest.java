package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.CheckoutGateway;
import com.fiap.fastfood.core.entity.Checkout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CheckoutUseCaseImplTest {

    @InjectMocks
    private CheckoutUseCaseImpl checkoutUseCase;

    @Test
    void submitTest() {
        final var gatewayMock = Mockito.mock(CheckoutGateway.class);
        final var checkoutMock = Mockito.mock(Checkout.class);

        Mockito.when(gatewayMock.save(checkoutMock))
                .thenReturn(checkoutMock);

        Assertions.assertNotNull(checkoutUseCase.submit(checkoutMock, gatewayMock));
    }

    @Test
    void findAllTest() {
        final var gatewayMock = Mockito.mock(CheckoutGateway.class);
        final var checkoutMock = Mockito.mock(Checkout.class);

        Mockito.when(gatewayMock.findAll())
                .thenReturn(List.of(checkoutMock));

        Assertions.assertNotNull(checkoutUseCase.findAll(gatewayMock));
    }
}
