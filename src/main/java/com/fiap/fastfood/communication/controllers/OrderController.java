package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.response.CreatedOrderResponse;
import com.fiap.fastfood.common.dto.response.GetOrderPaymentStatusResponse;
import com.fiap.fastfood.common.dto.response.GetOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderUseCase useCase;
    private final OrderGateway gateway;
    private final ProductGateway productGateway;
    private final CustomerGateway customerGateway;


    public OrderController(OrderGateway orderGateway, OrderUseCase orderUseCase, ProductGateway productGateway, CustomerGateway customerGateway) {
        this.gateway = orderGateway;
        this.useCase = orderUseCase;
        this.productGateway = productGateway;
        this.customerGateway = customerGateway;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreatedOrderResponse> createOrder(@RequestBody CreateOrderRequest request) throws OrderCreationException, NoSuchEntityException {
        final var result = useCase.createOrder(
                OrderBuilder.fromRequestToDomain(request),
                gateway,
                productGateway,
                customerGateway);

        return ResponseEntity.ok(OrderBuilder.fromDomainToCreatedResponse(result));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<GetOrderResponse>> getOrders() {
        final var result = useCase.listOrder(gateway);

        return ResponseEntity.ok(result.stream()
                .map(OrderBuilder::fromDomainToGetResponse)
                .collect(Collectors.toList()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(value = "/{orderId}/payment-status", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GetOrderPaymentStatusResponse> getOrderPaymentStatus(@PathVariable String orderId) throws EntityNotFoundException {
        return ResponseEntity.ok(GetOrderPaymentStatusResponse.builder()
                .paymentStatus(useCase.getOrderById(orderId, gateway).getPaymentStatus())
                .build());
    }

}
