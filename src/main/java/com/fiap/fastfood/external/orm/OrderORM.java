package com.fiap.fastfood.external.orm;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Order")
public class OrderORM {

    private String id;
    private Long customerId;
    private List<ItemORM> items;
    private BigDecimal totalValue;
    private String status;
    private String paymentStatus;
    private Date createdAt;
    private Date updatedAt;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public Long getCustomerId() {
        return customerId;
    }

    @DynamoDBAttribute
    public BigDecimal getTotalValue() {
        return totalValue;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    @DynamoDBAttribute
    public String getPaymentStatus() {
        return paymentStatus;
    }

    //@DynamoDBTypeConverted(converter = Converter.LocalDateTimeConverter.class)
    @DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.CREATE)
    public Date getCreatedAt() {
        return createdAt;
    }

    //@DynamoDBTypeConverted(converter = Converter.LocalDateTimeConverter.class)
    @DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.ALWAYS)
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @DynamoDBAttribute
    public List<ItemORM> getItems() {
        return items;
    }
}
