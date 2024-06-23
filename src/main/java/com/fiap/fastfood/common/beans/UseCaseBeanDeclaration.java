package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.usecase.CheckoutUseCase;
import com.fiap.fastfood.common.interfaces.usecase.OrderUseCase;
import com.fiap.fastfood.core.usecase.CheckoutUseCaseImpl;
import com.fiap.fastfood.core.usecase.OrderUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    @Bean
    public CheckoutUseCase checkoutUseCase() {
        return new CheckoutUseCaseImpl();
    }

    @Bean
    public OrderUseCase orderUseCase() {
        return new OrderUseCaseImpl();
    }

}