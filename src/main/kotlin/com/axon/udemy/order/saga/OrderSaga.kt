package com.axon.udemy.saga

import com.axon.udemy.order.core.OrderCreatedEvent
import com.axon.udemy.dependancy.commands.ReserveProductCommand
import com.axon.udemy.dependancy.events.ProductReservedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory

@Saga
class OrderSaga(
    @Transient
    private val commandGateway: CommandGateway
) {
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderCreatedEvent: OrderCreatedEvent) {
        val reserveProductCommand = ReserveProductCommand(
            productId = orderCreatedEvent.productId,
            quantity = orderCreatedEvent.quantity,
            orderId = orderCreatedEvent.orderId,
            userId = orderCreatedEvent.userId
        )

        LOGGER.info("OrderCreatedEvent handled for orderId {} and productId {}", orderCreatedEvent.orderId, orderCreatedEvent.productId)

        commandGateway.send(reserveProductCommand) { commandMessage, commandResultMessage ->
            if (commandResultMessage.isExceptional) {
                // Start compensating transaction
            }
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservedEvent: ProductReservedEvent) {
        // Process user payment
        LOGGER.info("ProductReservedEvent is called for orderId {} and productId {}", productReservedEvent.orderId, productReservedEvent.productId)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(OrderSaga::class.java)
    }
}