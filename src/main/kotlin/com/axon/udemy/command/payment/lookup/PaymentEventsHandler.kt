package com.axon.udemy.command.payment.lookup

import com.axon.udemy.command.payment.domain.events.PaymentProcessedEvent
import org.axonframework.eventhandling.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PaymentEventsHandler(private val paymentRepository: PaymentRepository) {
    @EventHandler
    fun on(event: PaymentProcessedEvent) {
        LOGGER.info("PaymentProcessedEvent is called for orderId: " + event.orderId)
        val paymentEntity = PaymentEntity(
            event.paymentId,
            event.orderId
        )
        paymentRepository.save(paymentEntity)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PaymentEventsHandler::class.java)
    }
}