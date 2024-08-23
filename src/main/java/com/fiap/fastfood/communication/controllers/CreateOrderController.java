package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.response.CreatedOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.NoSuchEntityException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.ProductGateway;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping("/orders")
public class CreateOrderController {

    private final OrderUseCase useCase;
    private final OrderGateway gateway;
    private final ProductGateway productGateway;
    private final CustomerGateway customerGateway;
    private final OrquestrationGateway orquestrationGateway;

    public CreateOrderController(OrderUseCase orderUseCase, OrderGateway orderGateway, ProductGateway productGateway, CustomerGateway customerGateway, OrquestrationGateway orquestrationGateway) {
        this.useCase = orderUseCase;
        this.gateway = orderGateway;
        this.productGateway = productGateway;
        this.customerGateway = customerGateway;
        this.orquestrationGateway = orquestrationGateway;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreatedOrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request)
            throws OrderCreationException, NoSuchEntityException {
        TransactionInformationStorage.putReceiveCount("1");
        TransactionInformationStorage.fill(new CustomMessageHeaders("sagaId", "orderId", "REQUEST", "OWASP"));

        final var result = useCase.createOrder(
                OrderBuilder.fromRequestToDomain(request),
                gateway,
                productGateway,
                customerGateway,
                orquestrationGateway);

        final var uri = URI.create(result.getId());
        return ResponseEntity.created(uri).body(OrderBuilder.fromDomainToCreatedResponse(result));
    }
}
