package com.axon.udemy.user.core

data class PaymentDetails(
    val name: String,
    val cardNumber: String,
    val validUntilMonth: Int,
    val validUntilYear: Int,
    val cvv: String
)