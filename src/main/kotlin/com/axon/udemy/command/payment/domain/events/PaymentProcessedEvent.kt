package com.axon.udemy.command.payment.domain.events

data class PaymentProcessedEvent(
    val orderId: String,
    val paymentId: String
)