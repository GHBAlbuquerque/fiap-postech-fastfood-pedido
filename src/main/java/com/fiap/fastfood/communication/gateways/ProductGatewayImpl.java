package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.core.entity.Product;
import com.fiap.fastfood.external.services.products.ProductHTTPClient;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductGatewayImpl implements ProductGateway {

    @Autowired
    private ProductHTTPClient productHTTPClient;


    @Override //TODO implementar
    public Product getProductByIdAndType(String id, String type) throws EntityNotFoundException {
        final var result = productHTTPClient.getProductByIdAndName(id,
                type,
                "",
                "",
                "application/json");

        if (result == null) {
            throw new EntityNotFoundException("ORDER-03", String.format("Product with ID %s on Order Item not found", id));
        }

        return null;
    }
}
