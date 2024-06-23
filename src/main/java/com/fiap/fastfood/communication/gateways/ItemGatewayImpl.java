package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.interfaces.gateways.ItemGateway;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.core.entity.Order;

import java.util.List;

public class ItemGatewayImpl implements ItemGateway {
    //TODO

    @Override
    public List<Item> listItem() {
        return null;
    }

    @Override
    public void saveItem(Order order) {

    }

    @Override
    public Order getItemByOrderId(String id) throws EntityNotFoundException {
        return null;
    }
}
