package com.fiap.fastfood.common.exceptions.custom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCodes {

    ORDER_01_NOT_FOUND,
    ORDER_02_ORDER_CREATION,
    ORDER_03_PRODUCT_ID_UNMATCH,
    ORDER_04_CUSTOMERID_UNMATCH,
    ORDER_05_PRODUCT_PRICE_UNMATCH,
    ORDER_06_MESSAGE_CREATION,
    ORDER_07_COMMAND_PROCESSING,
    ORDER_08_RESPONSE_SAGA,

}
