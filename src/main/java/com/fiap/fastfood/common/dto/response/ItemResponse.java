package com.fiap.fastfood.common.dto.response;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemResponse {
    private String productId;
    private String productType;
    private Integer quantity;
    private BigDecimal itemValue;
}
