package com.axon.udemy.query.order

data class OrderSummary(
    val orderId: String,
    val orderStatus: String,
    val message: String
)