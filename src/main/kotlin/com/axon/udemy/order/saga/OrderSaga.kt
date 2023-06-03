package com.axon.udemy.order.saga

import com.axon.udemy.order.core.OrderCreatedEvent
import com.axon.udemy.dependancy.commands.ReserveProductCommand
import com.axon.udemy.dependancy.events.ProductReservedEvent
import com.axon.udemy.order.command.domain.ApproveOrderCommand
import com.axon.udemy.order.command.domain.RejectOrderCommand
import com.axon.udemy.order.core.OrderApprovedEvent
import com.axon.udemy.payment.command.PaymentProcessedEvent
import com.axon.udemy.product.core.events.ProductReservationCancelledEvent
import com.axon.udemy.shared.commands.CancelProductReservationCommand
import com.axon.udemy.shared.commands.ProcessPaymentCommand
import com.axon.udemy.shared.events.OrderRejectedEvent
import com.axon.udemy.user.core.User
import com.axon.udemy.user.query.FetchUserPaymentDetailsQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.annotation.DeadlineHandler
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle.end
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

@Saga
class OrderSaga {
    @Transient
    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Transient
    @Autowired
    private lateinit var queryGateway: QueryGateway

    @Transient
    @Autowired
    private lateinit var deadlineManager: DeadlineManager

    private var deadlineId: String? = null

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderCreatedEvent: OrderCreatedEvent) {
        val reserveProductCommand = ReserveProductCommand(
            productId = orderCreatedEvent.productId,
            quantity = orderCreatedEvent.quantity,
            orderId = orderCreatedEvent.orderId,
            userId = orderCreatedEvent.userId
        )

        LOGGER.info(
            "OrderCreatedEvent handled for orderId {} and productId {}",
            orderCreatedEvent.orderId,
            orderCreatedEvent.productId
        )

        commandGateway.send(reserveProductCommand) { commandMessage, commandResultMessage ->
            // If isExceptional est dans le cas où il y a eu une exception et qu'il faut rollback la transaction et envoyer des événements de compensation
            if (commandResultMessage.isExceptional) {
                LOGGER.error(
                    "Error reserving product for orderId {}. Exception: {}",
                    orderCreatedEvent.orderId,
                    commandResultMessage.exceptionResult().message
                )
                // Start compensating transaction
            }
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservedEvent: ProductReservedEvent) {
        // Process user payment
        LOGGER.info(
            "ProductReservedEvent is called for orderId {} and productId {}",
            productReservedEvent.orderId,
            productReservedEvent.productId
        )

        val fetchUserPaymentDetailsQuery = FetchUserPaymentDetailsQuery(productReservedEvent.userId)
        // Query retourne une completable future
        val user = try {
            queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User::class.java))?.join()
        } catch (e: Exception) {
            LOGGER.error(
                "Error fetching user payment details for user {}. Exception: {}",
                productReservedEvent.userId,
                e.message
            )
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, e.message ?: "Could not fetch user payment details")
            return
        } ?: run {
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, "Could not fetch user payment details")
            return
        }

        LOGGER.info("Successfully fetched user payment details for user {}", productReservedEvent.userId)

        // Deadline pour revert la transaction si le payment n'est pas processé dans les 10 secondes
        deadlineId = deadlineManager.schedule(
            Duration.of(10, ChronoUnit.SECONDS),
            "payment-processing-deadline",
            productReservedEvent
        )

        val processPaymentCommand = ProcessPaymentCommand(
            paymentId = UUID.randomUUID().toString(),
            orderId = productReservedEvent.orderId,
            paymentDetails = user.paymentDetails
        )

        val result = try {
//            commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS)
            // No wait for deadline example
            commandGateway.sendAndWait(processPaymentCommand)
        } catch (e: Exception) {
            LOGGER.error(
                "Error processing payment for orderId {}. Exception: {}",
                productReservedEvent.orderId,
                e.message
            )
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, e.message ?: "Could not process user payment")
            return
        }

        if (result == null) {
            LOGGER.error("The ProcessPaymentCommand resulted in NULL for orderId {}", productReservedEvent.orderId)
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, "Could not process user payment")
            return
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(paymentProcessedEvent: PaymentProcessedEvent) {
        cancelDeadline()

        LOGGER.info("PaymentProcessedEvent is called for orderId {}", paymentProcessedEvent.orderId)

        val approveOrderCommand = ApproveOrderCommand(paymentProcessedEvent.orderId)
        commandGateway.send(approveOrderCommand) { commandMessage, commandResultMessage ->
            if (commandResultMessage.isExceptional) {
                LOGGER.error(
                    "Error approving order for orderId {}. Exception: {}",
                    paymentProcessedEvent.orderId,
                    commandResultMessage.exceptionResult().message
                )
                // Start compensating transaction
            }
        }
    }

    // Compensation flow
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservationCancelledEvent: ProductReservationCancelledEvent) {
        LOGGER.info("ProductReservationCancelledEvent is called for orderId {}", productReservationCancelledEvent.orderId)
        // Continue compensating transaction
        val rejectOrderCommand = RejectOrderCommand(productReservationCancelledEvent.orderId, productReservationCancelledEvent.reason)
        commandGateway.send<RejectOrderCommand>(rejectOrderCommand)
    }

    // Compensation flow
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderRejectedEvent: OrderRejectedEvent) {
        LOGGER.info("Successfully rejected order with orderId {}", orderRejectedEvent.orderId)
        // End compensating transaction
        end()
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderApprovedEvent: OrderApprovedEvent) {
        LOGGER.info("Saga is completed with OrderApprovedEvent for orderId {}", orderApprovedEvent.orderId)

//        SagaLifecycle.end() => Alternative à @EndSaga
    }

    // Handle deadline that is triggered in fun handle(productReservedEvent: ProductReservedEvent)
    @DeadlineHandler(deadlineName = "payment-processing-deadline")
    fun handlePaymentDeadline(productReservedEvent: ProductReservedEvent) {
        LOGGER.info("Payment processing deadline took place. Sending a compensating command to cancel order {}", productReservedEvent.orderId)
        // Start compensating transaction
        cancelProductReservation(productReservedEvent, "Payment timeout")
    }

    private fun cancelDeadline() {
        if (deadlineId != null) {
            deadlineManager.cancelSchedule("payment-processing-deadline", deadlineId ?: "")
        }
//        deadlineManager.cancelAll("payment-processing-deadline")
    }

    private fun cancelProductReservation(productReservedEvent: ProductReservedEvent, reason: String) {
        cancelDeadline()

        val publishProductReservationCommand = CancelProductReservationCommand(
            productId = productReservedEvent.productId,
            quantity = productReservedEvent.quantity,
            orderId = productReservedEvent.orderId,
            userId = productReservedEvent.userId,
            reason = reason
        )
        commandGateway.send<CancelProductReservationCommand>(publishProductReservationCommand)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(OrderSaga::class.java)
    }
}