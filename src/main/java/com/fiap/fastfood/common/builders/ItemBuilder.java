package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.core.entity.Item;
import com.fiap.fastfood.external.orm.ItemORM;

public class ItemBuilder {

    public static Item fromOrmToDomain(ItemORM orm) {
        return new Item()
                .setOrderId(orm.getOrderId())
                .setProductId(orm.getProductId())
                .setItemValue(orm.getItemValue())
                .setQuantity(orm.getQuantity());
    }

    public static ItemORM fromDomainToOrm(Item item) {
        return new ItemORM()
                .setOrderId(item.getOrderId())
                .setProductId(item.getProductId())
                .setItemValue(item.getItemValue())
                .setQuantity(item.getQuantity());
    }
}
