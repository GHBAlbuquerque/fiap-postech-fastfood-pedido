package com.fiap.fastfood.external.services.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GetCustomerResponse {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp;
}