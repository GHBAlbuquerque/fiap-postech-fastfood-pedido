package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void givenCreateOrderRequestThenRespondWithStatusCreated() {
        final var orderRequest = createOrderRequest();

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/orders")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void givenGetOrderThenRespondWithOrderListAndStatusOk() {

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void givenGetOrderPaymentStatusThenRespondWithGetOrderPaymentStatusResponse() {

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", "c96d9150-613d-440d-bd94-bf6eb76f7623")
                .when()
                .get("/orders/{id}/payment-status")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private CreateOrderRequest createOrderRequest() {
        final var item = new OrderItemRequest("productId", "SANDWICH", 1, BigDecimal.ONE);

        return new CreateOrderRequest(1L, List.of(item));
    }


}
