package com.fiap.fastfood.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer  {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String cpf;
    private String email;

}
