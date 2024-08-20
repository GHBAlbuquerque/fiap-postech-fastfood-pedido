package com.fiap.fastfood.external.services.customers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "mscliente",
        url = "${fiap.postech.fastfood.mscliente.url}"
)
public interface CustomerHTTPClient {

    @GetMapping(value = "/customers/{id}")
    ResponseEntity<GetCustomerResponse> getCustomerById(
            @PathVariable("id") final Long customerId,
            @RequestHeader("microsservice") final String microsservico,
            @RequestHeader("Content-Type") final String contentType
    );

    @GetMapping(value = "/customers")
    ResponseEntity<GetCustomerResponse> getCustomerByCpf(
            @RequestParam("cpf") final String customerCpf,
            @RequestHeader("microsservice") final String microsservico,
            @RequestHeader("Content-Type") final String contentType
    );
}
