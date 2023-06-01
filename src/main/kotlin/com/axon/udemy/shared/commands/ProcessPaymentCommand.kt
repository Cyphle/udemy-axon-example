package com.axon.udemy.shared.commands

import com.axon.udemy.user.core.PaymentDetails
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: String,
    val orderId: String,
    val paymentDetails: PaymentDetails
)