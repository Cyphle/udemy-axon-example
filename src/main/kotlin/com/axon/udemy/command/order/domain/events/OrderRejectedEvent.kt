package com.axon.udemy.command.order.domain.events

import com.axon.udemy.command.order.domain.OrderStatus

data class OrderRejectedEvent(
    val orderId: String,
    val reason: String,
    val orderStatus: OrderStatus = OrderStatus.REJECTED
)