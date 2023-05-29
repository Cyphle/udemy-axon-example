package com.axon.udemy.product.command.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

data class CreateProductCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val title: String,
    val price: BigDecimal,
    val quantity: Int
)