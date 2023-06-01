package com.axon.udemy.order.core

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OrderApprovedEvent(
    @TargetAggregateIdentifier
    val orderId: String,
    val orderStatus: OrderStatus = OrderStatus.APPROVED
)