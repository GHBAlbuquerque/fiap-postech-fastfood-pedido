package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Product;

import java.math.BigDecimal;

public interface ProductGateway {

    Product getProductByIdAndType(String id, String type) throws EntityNotFoundException;

    Boolean validateProductValue(BigDecimal value, Product product);

}
