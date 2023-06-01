package com.axon.udemy.order.saga

import com.axon.udemy.order.core.OrderCreatedEvent
import com.axon.udemy.dependancy.commands.ReserveProductCommand
import com.axon.udemy.dependancy.events.ProductReservedEvent
import com.axon.udemy.user.core.User
import com.axon.udemy.user.query.FetchUserPaymentDetailsQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory

@Saga
class OrderSaga(
    @Transient
    private val commandGateway: CommandGateway,
    @Transient
    private val queryGateway: QueryGateway
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
            // If isExceptional est dans le cas où il y a eu une exception et qu'il faut rollback la transaction et envoyer des événements de compensation
            if (commandResultMessage.isExceptional) {
                // Start compensating transaction
            }
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservedEvent: ProductReservedEvent) {
        // Process user payment
        LOGGER.info("ProductReservedEvent is called for orderId {} and productId {}", productReservedEvent.orderId, productReservedEvent.productId)

        val fetchUserPaymentDetailsQuery = FetchUserPaymentDetailsQuery(productReservedEvent.userId)
        // Query retourne une completable future
        val user = try {
            queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User::class.java)).join()
        } catch (e: Exception) {
            LOGGER.error("Error fetching user payment details for user {}. Exception: {}", productReservedEvent.userId, e.message)
            // Start compensating transaction
            return
        } ?: run {
            // Start compensating transaction
            return
        }

        LOGGER.info("Successfully fetched user payment details for user {}", productReservedEvent.userId)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(OrderSaga::class.java)
    }
}