package com.axon.udemy.command.domain

import com.axon.udemy.core.OrderCreatedEvent
import com.axon.udemy.core.OrderStatus
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

    @EventSourcingHandler
    fun on(orderCreatedEvent: OrderCreatedEvent) {
        orderId = orderCreatedEvent.orderId
        productId = orderCreatedEvent.productId
        userId = orderCreatedEvent.userId
        quantity = orderCreatedEvent.quantity
        addressId = orderCreatedEvent.addressId
        orderStatus = orderCreatedEvent.orderStatus
    }
}