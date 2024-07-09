package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenCreateOrderRequestThenRespondWithStatusCreated() {
        final var orderRequest = createOrder();

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(orderRequest)
                .when()
                .post("/orders")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);
    }

    @Test
    void givenGetOrderThenRespondWithOrderListAndStatusOk() {

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/orders")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

    @Test
    void givenGetOrderPaymentStatusThenRespondWithGetOrderPaymentStatusResponse() {

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .pathParam("id", "c96d9150-613d-440d-bd94-bf6eb76f7623")
                .when()
                .get("/orders/{id}/payment-status")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

    private CreateOrderRequest createOrder() {
        final var item = new OrderItemRequest("productId", "SANDWICH", 1,  BigDecimal.ONE);

        return new CreateOrderRequest(1L, List.of(item));
    }


}
