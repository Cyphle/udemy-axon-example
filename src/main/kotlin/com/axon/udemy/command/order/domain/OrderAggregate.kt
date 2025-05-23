package com.axon.udemy.command.order.domain

import com.axon.udemy.command.order.domain.events.OrderApprovedEvent
import com.axon.udemy.command.order.domain.events.OrderCreatedEvent
import com.axon.udemy.command.order.domain.events.OrderRejectedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class OrderAggregate {
    @AggregateIdentifier
    var orderId: String? = null
    var productId: String? = null
    var userId: String? = null
    var quantity: Int = 0
    var addressId: String? = null
    var orderStatus: OrderStatus? = null

    constructor()

    @CommandHandler
    constructor(createOrderCommand: CreateOrderCommand): this() {
        val event = OrderCreatedEvent(
            orderId = createOrderCommand.orderId,
            productId = createOrderCommand.productId,
            userId = createOrderCommand.userId,
            quantity = createOrderCommand.quantity,
            addressId = createOrderCommand.addressId,
            orderStatus = createOrderCommand.orderStatus
        )

        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun handle(approveOrderCommand: ApproveOrderCommand) {
        val event = OrderApprovedEvent(
            orderId = approveOrderCommand.orderId
        )

        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun handle(rejectOrderCommand: RejectOrderCommand) {
        val event = OrderRejectedEvent(
            orderId = rejectOrderCommand.orderId,
            reason = rejectOrderCommand.reason
        )

        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(orderCreatedEvent: OrderCreatedEvent) {
        orderId = orderCreatedEvent.orderId
        productId = orderCreatedEvent.productId
        userId = orderCreatedEvent.userId
        quantity = orderCreatedEvent.quantity
        addressId = orderCreatedEvent.addressId
        orderStatus = orderCreatedEvent.orderStatus
    }

    @EventSourcingHandler
    fun on(orderApprovedEvent: OrderApprovedEvent) {
        orderStatus = orderApprovedEvent.orderStatus
    }

    @EventSourcingHandler
    fun on(orderRejectedEvent: OrderRejectedEvent) {
        orderStatus = orderRejectedEvent.orderStatus
    }
}