package com.axon.udemy.command.order.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ApproveOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String
)