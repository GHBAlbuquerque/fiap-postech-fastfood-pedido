package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;

public interface ItemUseCase {

    Boolean validateItemProduct(String productId, ProductGateway productGateway) throws NoSuchEntityException;
}
