package com.axon.udemy.order.command.infrastructure.api

data class CreateOrderRestModel(
    val productId: String,
    val quantity: Int,
    val addressId: String
)