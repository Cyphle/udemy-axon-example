package com.axon.udemy.order.command.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ApproveOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String
)