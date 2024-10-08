package com.fiap.fastfood.core.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private String id;
    private Long customerId;
    private List<Item> items;
    private BigDecimal totalValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
    private OrderPaymentStatus paymentStatus;

    public BigDecimal getTotalValue() {
        return items.stream()
                .map(Item::getTotalItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order(String id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }
}
