package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.external.orm.ItemORM;

public class ItemBuilder {

    public static Item fromOrmToDomain(ItemORM orm) {
        return new Item()
                .setProductId(orm.getProductId())
                .setItemValue(orm.getItemValue())
                .setQuantity(orm.getQuantity());
    }

    public static ItemORM fromDomainToOrm(Item item) {
        return new ItemORM()
                .setProductId(item.getProductId())
                .setItemValue(item.getItemValue())
                .setQuantity(item.getQuantity());
    }

    public static Item fromRequestToDomain(OrderItemRequest request) {
        return new Item()
                .setProductId(request.getProductId())
                .setQuantity(request.getQuantity())
                .setItemValue(request.getProductValue());

    }
}
