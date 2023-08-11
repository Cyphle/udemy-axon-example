package com.axon.udemy.command.order.domain.events

import com.axon.udemy.command.order.domain.OrderStatus

data class OrderApprovedEvent(
    val orderId: String,
    val orderStatus: OrderStatus = OrderStatus.APPROVED
)