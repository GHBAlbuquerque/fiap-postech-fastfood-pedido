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

    @GetMapping(value = "/products/{id}")
    ResponseEntity<GetProductResponse> getProductByIdAndType(
            @PathVariable("id") final String productId,
            @RequestParam("type") final String type,
            @RequestHeader("microsservice") final String microsservico,
            @RequestHeader("Content-Type") final String contentType
    );
}
