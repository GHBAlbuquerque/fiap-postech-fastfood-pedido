package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.interfaces.usecase.ItemUseCase;

public class ItemUseCaseImpl implements ItemUseCase {

    @Override
    public Boolean validateItemProduct(String productId) throws NoSuchEntityException {
        //TODO: implementar validacao de existencia de produto por codigo batendo em produtos via ProductGateway com Feign
        //TODO: validar preco marcado do produto e no item
        return null;
    }

}
