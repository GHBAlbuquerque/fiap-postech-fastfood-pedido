package com.fiap.fastfood.bdd;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import com.fiap.fastfood.common.dto.response.CreatedOrderResponse;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinition {

    @Value("${server.port}")
    private int port = 3000;

    private Response response;

    private CreateOrderRequest createOrderRequest;

    private CreatedOrderResponse createdOrderResponse;

    private final String ENDPOINT_API_CREATE_ORDER = "/orders";


    @Given("a new valid order request with valid products")
    public void aNewValidOrderRequestWithValidProducts() {
        var createOrderRequest = createValidOrderRequest();

        response = given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createOrderRequest)
                .when()
                .post(ENDPOINT_API_CREATE_ORDER);
    }

    @When("a new order is succesfully created")
    public void aNewOrderIsSuccesfullyCreated() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .log().ifValidationFails();

    }

    @Then("an order id is received")
    public void anOrderIdIsReceived() {
        response.then()
                .log().ifValidationFails()
                .body(Matchers.typeCompatibleWith(CreatedOrderResponse.class));
    }

    @Given("a new invalid order request with non-existant product")
    public void aNewInvalidOrderRequestWithNonExistantProduct() {
        var createOrderRequest = createInvalidOrderRequest();

        response = given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createOrderRequest)
                .when()
                .post(ENDPOINT_API_CREATE_ORDER);
    }

    @When("order creation fails")
    public void orderCreationFails() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .log().ifValidationFails();
    }

    @Then("an error is throwed with a message")
    public void anErrorIsThrowedWithAMessage() {
        response.then()
                .log().ifValidationFails()
                .body(Matchers.typeCompatibleWith(ExceptionDetails.class));
    }


    private CreateOrderRequest createValidOrderRequest() {
        final var item = new OrderItemRequest("productId", "SANDWICH", 1, BigDecimal.ONE);

        return new CreateOrderRequest(1L, List.of(item));
    }

    private CreateOrderRequest createInvalidOrderRequest() {
        final var item = new OrderItemRequest("no-product-id", "SANDWICH", 1, BigDecimal.ONE);

        return new CreateOrderRequest(2L, List.of(item));
    }
}



