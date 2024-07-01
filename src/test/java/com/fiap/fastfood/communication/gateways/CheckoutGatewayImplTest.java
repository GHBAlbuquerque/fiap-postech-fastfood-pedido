package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.CheckoutRepository;
import com.fiap.fastfood.core.entity.Checkout;
import com.fiap.fastfood.external.orm.CheckoutORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CheckoutGatewayImplTest {

    @InjectMocks
    CheckoutGatewayImpl checkoutGateway;

    @Mock
    CheckoutRepository checkoutRepository;

    @Test
    void saveTest() {
        final var checkoutMock = Mockito.mock(Checkout.class);
        final var checkoutORMMock = Mockito.mock(CheckoutORM.class);

        Mockito.when(checkoutRepository.save(any()))
                .thenReturn(checkoutORMMock);

        Assertions.assertNotNull(
                checkoutGateway.save(checkoutMock)
        );
    }

    @Test
    void findAllTest() {
        final var checkoutORMMock = Mockito.mock(CheckoutORM.class);

        Mockito.when(checkoutRepository.findAllByOrderIdOrderByCreatedAtAsc())
                .thenReturn(List.of(checkoutORMMock));

        final var result = checkoutGateway.findAll();

        Assertions.assertFalse(result.isEmpty());
    }

}
