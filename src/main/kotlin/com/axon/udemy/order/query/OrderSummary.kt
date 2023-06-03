package com.axon.udemy.order.query

data class OrderSummary(
    val orderId: String,
    val orderStatus: String,
    val message: String
)