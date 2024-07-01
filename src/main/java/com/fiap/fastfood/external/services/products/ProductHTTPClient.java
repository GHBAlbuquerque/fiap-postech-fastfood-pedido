package com.fiap.fastfood.external.services.products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "msproduto",
        url = "${fiap.postech.fastfood.msproduto.url}"
)
public interface ProductHTTPClient {

    @GetMapping(value = "/products/{id}") //TODO conferir path
    ResponseEntity<GetProductResponse> getProductByIdAndType(
            @PathVariable("id") final String productId,
            @RequestParam("type") final String type,
            //@RequestHeader("cpf_cliente") final String cpfCliente,
            //@RequestHeader("senha_cliente") final String senhaCliente,
            @RequestHeader("Content-Type") final String contentType
    );
}
