package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.core.entity.Product;
import com.fiap.fastfood.external.services.products.GetProductResponse;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ProductGatewayImplTest {

    @InjectMocks
    private ProductGatewayImpl productGateway;

    @Mock
    private ProductHTTPClient productHTTPClient;

    @Test
    void getProductByIdAndTypeTest() {
        final var productResponseMock = Mockito.mock(GetProductResponse.class);

        final var response = ResponseEntity.ok(productResponseMock);

        Mockito.when(productHTTPClient.getProductByIdAndType(anyString(), anyString(),  anyString(), anyString()))
                .thenReturn(response);

        Assertions.assertDoesNotThrow(() -> productGateway.getProductByIdAndType("id", "type"));
    }

    @Test
    void validateProductValueTest() {
        final var productMock = Mockito.mock(Product.class);

        Mockito.when(productMock.getPrice())
                .thenReturn(BigDecimal.ONE);

        Assertions.assertTrue(
                productGateway.validateProductValue(BigDecimal.ONE, productMock)
        );
    }
}
