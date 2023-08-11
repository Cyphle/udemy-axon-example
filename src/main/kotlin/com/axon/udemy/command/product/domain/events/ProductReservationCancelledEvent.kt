package com.axon.udemy.command.product.domain.events

data class ProductReservationCancelledEvent(
    val productId: String,
    val quantity: Int,
    val orderId: String,
    val userId: String,
    val reason: String
)