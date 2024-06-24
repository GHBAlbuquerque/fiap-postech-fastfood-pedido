package com.fiap.fastfood.core.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    String id;
    String name;
    BigDecimal price;
    String description;
    String type;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
