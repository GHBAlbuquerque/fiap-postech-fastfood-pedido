package com.fiap.fastfood.external.services.products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "fiap_postech_fastfood_ms_product",
        url = "${fiap.postech.fastfood.gateway.url}"
)
public interface ProductHTTPClient {

    @GetMapping(value = "/products/{id}") //TODO conferir path
    ResponseEntity<GetProductResponse> getProduct(
            @PathVariable("id") final String productId,
            @RequestHeader("cpf_cliente") final String cpfCliente,
            @RequestHeader("senha_cliente") final String senhaCliente,
            @RequestHeader("Content-Type") final String contentType
    );
}
