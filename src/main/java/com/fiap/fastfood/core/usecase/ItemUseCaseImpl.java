package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.CreateEntityException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.interfaces.gateways.ItemGateway;
import com.fiap.fastfood.common.interfaces.usecase.ItemUseCase;
import com.fiap.fastfood.core.entity.Item;

public class ItemUseCaseImpl implements ItemUseCase {

    @Override
    public void createItem(Item item, ItemGateway itemGateway) throws NoSuchEntityException, CreateEntityException {
        //TODO: implementar criacao de item
    }

    @Override
    public Boolean validateItemProduct(String productId) throws NoSuchEntityException {
        //TODO: implementar validacao de existencia de produto por codigo batendo em produtos via ProductGateway com Feign
        return null;
    }

}
