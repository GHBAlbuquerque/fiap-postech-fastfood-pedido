package com.fiap.fastfood.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private String productId;
    private String productType;
    private Integer quantity;
    private BigDecimal productValue;
}
