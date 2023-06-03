package com.axon.udemy.shared.events

import com.axon.udemy.order.core.OrderStatus

data class OrderRejectedEvent(
    val orderId: String,
    val reason: String,
    val orderStatus: OrderStatus = OrderStatus.REJECTED
)