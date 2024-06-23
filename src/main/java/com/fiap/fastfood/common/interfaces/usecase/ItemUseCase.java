package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.CreateEntityException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.interfaces.gateways.ItemGateway;
import com.fiap.fastfood.core.entity.Item;

public interface ItemUseCase {

    void createItem(Item item, ItemGateway itemGateway) throws NoSuchEntityException, CreateEntityException;

    Boolean validateItemProduct(String productId) throws NoSuchEntityException;
    //TODO: implementar validacao de existencia de produto por codigo batendo em produtos
}
