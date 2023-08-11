package com.axon.udemy.command.order.infrastructure

data class CreateOrderRestModel(
    val productId: String,
    val quantity: Int,
    val addressId: String
)