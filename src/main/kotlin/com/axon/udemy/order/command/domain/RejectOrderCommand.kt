package com.axon.udemy.order.command.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RejectOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val reason: String
)