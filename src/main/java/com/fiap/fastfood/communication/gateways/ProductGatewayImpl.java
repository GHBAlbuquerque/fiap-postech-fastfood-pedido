package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.ProductBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.core.entity.Product;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;

import java.math.BigDecimal;

public class ProductGatewayImpl implements ProductGateway {

    private ProductHTTPClient productHTTPClient;

    private static final String CONTENT_TYPE = "application/json";
    private static final String MICROSSERVICE = "ms_produto";

    public ProductGatewayImpl(ProductHTTPClient productHTTPClient) {
        this.productHTTPClient = productHTTPClient;
    }

    @Override
    public Product getProductByIdAndType(String id, String type) throws EntityNotFoundException {
        final var result = productHTTPClient.getProductByIdAndType(id, type, MICROSSERVICE, CONTENT_TYPE);
        final var response = result.getBody();

        if (response == null) {
            throw new EntityNotFoundException(
                    "ORDER-03",
                    String.format("Product with ID %s on Order Item not found", id)
            );
        }

        return ProductBuilder.fromResponseToDomain(response);
    }

    @Override
    public Boolean validateProductValue(BigDecimal value, Product product) {
        return value.equals(product.getPrice());
    }
}
