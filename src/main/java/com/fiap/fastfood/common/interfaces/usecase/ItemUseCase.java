package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;

public interface ItemUseCase {

    Boolean validateItemProduct(String productId) throws NoSuchEntityException;
    //TODO: implementar validacao de existencia de produto por codigo batendo em produtos
}
