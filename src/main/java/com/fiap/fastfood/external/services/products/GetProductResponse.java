package com.fiap.fastfood.external.services.products;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {

    String name;
    BigDecimal price;
    String description;
    String type;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
