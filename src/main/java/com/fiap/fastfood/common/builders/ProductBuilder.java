package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.core.entity.Product;
import com.fiap.fastfood.external.services.products.GetProductResponse;

public class ProductBuilder {

    public static Product fromResponseToDomain(GetProductResponse response) {
        return new Product()
                .setId(response.getId())
                .setName(response.getDescription())
                .setDescription(response.getDescription())
                .setType(response.getType())
                .setCreatedAt(response.getCreatedAt())
                .setUpdatedAt(response.getUpdatedAt());
    }
}
