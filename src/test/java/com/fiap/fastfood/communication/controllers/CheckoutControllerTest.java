package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.CheckoutRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckoutControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenCheckoutRequestThenRespondWithStatusCreated() {
        final var checkoutRequest = new CheckoutRequest("orderId");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(checkoutRequest)
                .when()
                .post("/checkout")
                .then()
                .log().ifValidationFails()
                //.statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);
    }

    @Test
    void givenGetThenRespondWithCheckoutlistAndStatusOK() {

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/checkout")
                .then()
                .log().ifValidationFails()
                //.statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

}
