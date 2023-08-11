package com.axon.udemy.command.order.domain.events

import com.axon.udemy.command.order.domain.OrderStatus

data class OrderCreatedEvent(
    val orderId: String,
    val productId: String,
    val userId: String,
    val quantity: Int,
    val addressId: String,
    val orderStatus: OrderStatus
)