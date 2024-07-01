package com.fiap.fastfood.communication.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenCreateOrderRequestThenRespondWithStatusCreated() {

    }

    @Test
    void givenGetOrderThenRespondWithOrderListAndStatusOk() {

    }

    @Test
    void givenGetOrderPaymentStatusThenRespondWithGetOrderPaymentStatusResponse() {

    }


}
