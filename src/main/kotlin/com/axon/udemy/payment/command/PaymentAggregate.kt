package com.axon.udemy.payment.command

import com.axon.udemy.shared.commands.ProcessPaymentCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class PaymentAggregate {
    @AggregateIdentifier
    private var paymentId: String? = null
    private var orderId: String? = null

    constructor()

    @CommandHandler
    constructor(processPaymentCommand: ProcessPaymentCommand) {
        requireNotNull(processPaymentCommand.paymentDetails) { "Missing payment details" }
        requireNotNull(processPaymentCommand.orderId) { "Missing orderId" }
        requireNotNull(processPaymentCommand.paymentId) { "Missing paymentId" }

        AggregateLifecycle.apply(
            PaymentProcessedEvent(
                processPaymentCommand.orderId,
                processPaymentCommand.paymentId
            )
        )
    }

    @EventSourcingHandler
    protected fun on(paymentProcessedEvent: PaymentProcessedEvent) {
        paymentId = paymentProcessedEvent.paymentId
        orderId = paymentProcessedEvent.orderId
    }
}