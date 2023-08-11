package com.axon.udemy.command.order.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RejectOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val reason: String
)