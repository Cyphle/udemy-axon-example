package com.axon.udemy.command.product.domain.events

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProductReservedEvent(
    @TargetAggregateIdentifier
    val productId: String,
    val quantity: Int,
    val orderId: String,
    val userId: String
)