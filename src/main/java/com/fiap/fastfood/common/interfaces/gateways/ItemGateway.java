package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.core.entity.Order;

import java.util.List;

public interface ItemGateway {

    List<Item> listItem();

    void saveItem(Order order);

    Order getItemByOrderId(String id) throws EntityNotFoundException;

}
