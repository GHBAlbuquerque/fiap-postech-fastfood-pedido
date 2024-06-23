package com.fiap.fastfood.external.orm;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class ItemORM {

    private String productId;
    private Integer quantity;
    private BigDecimal itemValue;

    @DynamoDBAttribute
    public String getProductId() {
        return productId;
    }

    @DynamoDBAttribute
    public Integer getQuantity() {
        return quantity;
    }

    @DynamoDBAttribute
    public BigDecimal getItemValue() {
        return itemValue;
    }

}