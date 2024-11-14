package com.langonifood.order.dto;

public enum PaymentStatus {
    CREATED,
    CONFIRMED,
    CANCELLED,
    CONFIRMED_WITHOUT_INTEGRATION_WITH_ORDER
}
