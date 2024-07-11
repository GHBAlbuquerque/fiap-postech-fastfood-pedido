package com.fiap.fastfood.core.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Product {

    String id;
    String name;
    BigDecimal price;
    String description;
    String type;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
