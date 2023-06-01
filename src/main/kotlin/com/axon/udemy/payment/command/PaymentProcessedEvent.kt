package com.axon.udemy.payment.command

data class PaymentProcessedEvent(
    val orderId: String,
    val paymentId: String
)