package com.axon.udemy.order.core

data class OrderCreatedEvent(
    val orderId: String,
    val productId: String,
    val userId: String,
    val quantity: Int,
    val addressId: String,
    val orderStatus: OrderStatus
)