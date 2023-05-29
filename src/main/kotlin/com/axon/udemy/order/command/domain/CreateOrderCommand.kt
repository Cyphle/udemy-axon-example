package com.axon.udemy.command.domain

import com.axon.udemy.core.OrderStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val userId: String,
    val productId: String,
    val quantity: Int,
    val addressId: String,
    val orderStatus: OrderStatus
)
