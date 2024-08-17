package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[RESPONSE] SagaId: {} | Command received from {}. ";
    public static final String COMMAND_END_LOG = "[RESPONSE] SagaId: {} | Command successfully received from {}.";
    public static final String COMMAND_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving command from {}. | Error Message: {} | Message: {}";

    public static final String RESPONSE_INIT_LOG = "[COMMAND] SagaId: {} | Sending Response to {}...";
    public static final String RESPONSE_END_LOG = "[COMMAND] SagaId: {} | Response Succesfully sent to {}.";
    public static final String RESPONSE_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending response to {}. | Error Message: {} | Message: {}";

    public static final String ORDER_CREATION_INIT_LOG = "[COMMAND] SagaId: {} | Creating order. | Message: {}";
    public static final String ORDER_CREATION_END_LOG = "[COMMAND] SagaId: {} | Order created.";
    public static final String ORDER_CREATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error creating order. | Error Message: {} ";
}
