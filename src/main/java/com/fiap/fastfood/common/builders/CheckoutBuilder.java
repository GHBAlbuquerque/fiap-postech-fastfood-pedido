package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.CheckoutRequest;
import com.fiap.fastfood.common.dto.response.CheckoutResponse;
import com.fiap.fastfood.common.utils.TimeConverter;
import com.fiap.fastfood.core.entity.Checkout;
import com.fiap.fastfood.external.orm.CheckoutORM;

public class CheckoutBuilder {

    public static Checkout fromRequestToDomain(CheckoutRequest request) {
        return Checkout.builder()
                .status("In Progress")
                .orderId(request.getOrderId())
                .build();
    }

    public static CheckoutResponse fromDomainToResponse(Checkout checkout) {
        if (checkout == null) return null;

        return CheckoutResponse.builder()
                .id(checkout.getId())
                .status(checkout.getStatus())
                .orderId(checkout.getOrderId())
                .createdAt(checkout.getCreatedAt())
                .build();
    }

    public static Checkout fromOrmToDomain(CheckoutORM orm) {
        if (orm == null) return null;

        return Checkout.builder()
                .id(orm.getId())
                .status(orm.getStatus())
                .orderId(orm.getOrderId())
                .build();
    }

    public static CheckoutORM fromDomainToOrm(Checkout checkout) {
        var checkoutORM = CheckoutORM.builder()
                .id(checkout.getId())
                .status(checkout.getStatus())
                .orderId(checkout.getOrderId())
                .build();

        if (checkout.getCreatedAt() != null)
            checkoutORM.setCreatedAt(TimeConverter.convertToDateViaInstant(checkout.getCreatedAt()));

        return checkoutORM;
    }
}
