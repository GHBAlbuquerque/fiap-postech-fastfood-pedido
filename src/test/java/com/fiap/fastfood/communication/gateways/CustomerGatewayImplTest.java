package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Customer;
import com.fiap.fastfood.external.services.customers.CustomerHTTPClient;
import com.fiap.fastfood.external.services.customers.GetCustomerResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class CustomerGatewayImplTest {

    @InjectMocks
    private CustomerGatewayImpl customerGateway;

    @Mock
    private CustomerHTTPClient customerHTTPClient;

    @Test
    void getCustomerByIdTest() {
        final var customerResponseMock = Mockito.mock(GetCustomerResponse.class);
        final var customerMock = Mockito.mock(Customer.class);

        Mockito.when(customerHTTPClient.getCustomerById(anyLong(), anyString()))
                .thenReturn(ResponseEntity.ok(customerResponseMock));

        Assertions.assertDoesNotThrow(
                () -> customerGateway.getCustomerById(1L)
        );
    }

    @Test
    void getCustomerByIdErrorTest() {
        Mockito.when(customerHTTPClient.getCustomerById(anyLong(), anyString()))
                .thenReturn(ResponseEntity.ok(null));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> customerGateway.getCustomerById(1L)
        );
    }
}
