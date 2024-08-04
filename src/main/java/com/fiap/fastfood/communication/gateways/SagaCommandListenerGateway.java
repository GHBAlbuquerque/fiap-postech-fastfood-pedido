package com.fiap.fastfood.communication.gateways;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SagaCommandListenerGateway {

    private static final Logger logger = LogManager.getLogger(SagaCommandListenerGateway.class);

    @SqsListener("${aws.queue_comando_criar_pedido.url}")
    public void listenerCreateOrder(String message) {
        System.out.println(message);
    }

    @SqsListener("${aws.queue_comando_preparar_pedido.url}")
    public void listenerPrepareOrder(String message) {
        System.out.println(message);
    }

    @SqsListener("${aws.comando_cancelar_pedido.url}")
    public void listenerCancelOrder(String message) {
        System.out.println(message);
    }
}
