package com.fiap.fastfood.external.services.customers;

import com.fiap.fastfood.external.services.products.GetProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "fiap_postech_fastfood_ms_client",
        url = "${fiap.postech.fastfood.gateway.url}"
)
public interface CustomerHTTPClient {

    @GetMapping(value = "/customers") //TODO conferir path
    ResponseEntity<GetCustomerResponse> getCustomerByCpf(
            @RequestParam("cpf") final String customerCpf,
            @RequestHeader("cpf_cliente") final String cpfCliente,
            @RequestHeader("senha_cliente") final String senhaCliente,
            @RequestHeader("Content-Type") final String contentType
    );
}
