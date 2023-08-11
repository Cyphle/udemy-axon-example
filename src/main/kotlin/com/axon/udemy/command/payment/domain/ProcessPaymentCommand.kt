package com.axon.udemy.command.payment.domain

import com.axon.udemy.command.user.domain.PaymentDetails
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: String,
    val orderId: String,
    val paymentDetails: PaymentDetails
)