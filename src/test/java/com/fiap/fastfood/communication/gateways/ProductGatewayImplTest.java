package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductGatewayImplTest {

    @InjectMocks
    private ProductGatewayImpl productGateway;

    @Mock
    private ProductHTTPClient productHTTPClient;

    @Test
    void getProductByIdAndTypeTest() {

    }

    @Test
    void validateProductValueTest() {

    }
}
