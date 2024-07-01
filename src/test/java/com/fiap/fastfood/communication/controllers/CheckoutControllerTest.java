package com.fiap.fastfood.communication.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckoutControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenCheckoutRequestThenRespondWithStatusCreated() {

    }

    @Test
    void givenGetThenRespondWithCheckoutlistAndStatusOK() {

    }

}
